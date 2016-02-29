package ru.jewelline.request.http.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpResponseReceiver;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.NullResponseReceiver;
import ru.jewelline.request.http.config.HttpConfiguration;
import ru.jewelline.request.http.config.SimpleHttpConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestFactoryImplTest {

    private final HttpConfiguration httpConfig = new SimpleHttpConfiguration();
    @Mock
    private HttpURLConnection connection;

    @Test
    public void test_copyNullSourceToDestination() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        HttpRequestFactoryImpl.copyStreams(null, outputStream);
        verifyZeroInteractions(outputStream);
    }

    @Test
    public void test_copySourceToNull() throws IOException {
        InputStream source = mock(InputStream.class);
        HttpRequestFactoryImpl.copyStreams(source, null);
        verifyZeroInteractions(source);
    }

    @Test
    public void test_correctCopyStreams() throws IOException {
        byte[] payload = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        ByteArrayInputStream source = new ByteArrayInputStream(payload);
        ByteArrayOutputStream destination = new ByteArrayOutputStream();
        HttpRequestFactoryImpl.copyStreams(source, destination);
        assertThat(destination.toByteArray()).isEqualTo(payload);
    }

    @Test
    public void test_noExceptionOnClose() throws IOException {
        byte[] payload = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        InputStream source = new ByteArrayInputStream(payload);
        OutputStream destination = mock(OutputStream.class);
        doThrow(new IOException("test")).when(destination).close();
        HttpRequestFactoryImpl.copyStreams(source, destination);

        verify(destination).flush();
        //assert no exception
    }

    private HttpRequestFactoryImpl testInstance() {
        return new HttpRequestFactoryImpl(this.httpConfig) {
            @Override
            HttpURLConnection createConnection(HttpRequest request) throws IOException {
                return HttpRequestFactoryImplTest.this.connection;
            }
        };
    }

    @Test
    public void test_clientCanReturnNewBuilderInstance() {
        HttpRequestFactoryImpl requestFactory = testInstance();
        assertThat(requestFactory.newRequest() != requestFactory.newRequest()).isTrue();
    }

    @Test(expected = NetworkException.class)
    public void test_executeWithNullRequest() {
        testInstance().execute(null, NullResponseReceiver.getInstance());
    }

    @Test
    public void test_configureConnectionWithoutHeaders() {
        HttpResponseReceiver response = mock(HttpResponseReceiver.class);
        HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);
        when(request.getHeaders()).thenReturn(Collections.<String, List<String>>emptyMap());

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).setConnectTimeout(Matchers.anyInt());
        verify(connection, never()).addRequestProperty(anyString(), anyString());
        verify(connection, never()).setRequestProperty(anyString(), anyString());
    }

    @Test
    public void test_configureConnectionWithHeaders() {
        HttpResponseReceiver response = mock(HttpResponseReceiver.class);
        HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("key1", Collections.singletonList("value1"));
        headers.put("key2", Collections.singletonList("value2"));
        when(request.getHeaders()).thenReturn(headers);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).setConnectTimeout(Matchers.anyInt());
        verify(connection).addRequestProperty("key1", "value1");
        verify(connection).addRequestProperty("key2", "value2");
    }

    @Test
    public void test_verifySetResponseCode() throws IOException {
        HttpResponseReceiver response = mock(HttpResponseReceiver.class);
        OutputStream outputStream = mock(OutputStream.class);
        when(response.getResponseStream()).thenReturn(outputStream);
        HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);
        int responseCode = 200;
        when(connection.getResponseCode()).thenReturn(responseCode);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).getResponseCode();
        verify(response).setResponseCode(responseCode);
    }

    @Test
    public void test_pickDataStreamForGoodCode() throws IOException {
        HttpResponseReceiver response = mock(HttpResponseReceiver.class);
        OutputStream outputStream = mock(OutputStream.class);
        when(response.getResponseStream()).thenReturn(outputStream);
        HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        int responseCode = 201;
        InputStream inputStream = mock(InputStream.class);
        when(connection.getInputStream()).thenReturn(inputStream);
        when(connection.getResponseCode()).thenReturn(responseCode);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).getInputStream();
        verify(outputStream).flush(); // verify that stream was copied
    }

    @Test
    public void test_pickErrorStreamForBadCode() throws IOException {
        HttpResponseReceiver response = mock(HttpResponseReceiver.class);
        OutputStream outputStream = mock(OutputStream.class);
        when(response.getErrorStream()).thenReturn(outputStream);
        HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        int responseCode = 400;
        when(connection.getResponseCode()).thenReturn(responseCode);
        InputStream inputStream = mock(InputStream.class);
        when(connection.getErrorStream()).thenReturn(inputStream);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).getErrorStream();
        verify(outputStream).flush(); // verify that stream was copied
    }

    @Test
    public void test_doNotCopyResponseStreamToNull() throws IOException {
        HttpResponseReceiver response = mock(HttpResponseReceiver.class);
        HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        when(connection.getResponseCode()).thenReturn(200);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection, never()).getInputStream();
        verify(connection, never()).getErrorStream();
    }

    @Test
    public void test_doNotCopyErrorStreamToNull() throws IOException {
        HttpResponseReceiver response = mock(HttpResponseReceiver.class);
        HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        when(connection.getResponseCode()).thenReturn(401);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection, never()).getInputStream();
        verify(connection, never()).getErrorStream();
    }

    @Test
    public void test_doAdditionalAttemptsInCaseOfNoResponseCode() throws Exception {
        HttpResponseReceiver response = mock(HttpResponseReceiver.class);
        HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        when(connection.getResponseCode()).thenReturn(-1);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(request, times(this.httpConfig.getRetryCount())).getMethod();
    }

    @Test
    public void test_setResponseHeaders() {
        String hKey = "key";
        List<String> hValue = Collections.singletonList("value");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put(hKey, hValue);
        HttpResponseReceiver response = mock(HttpResponseReceiver.class);
        HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        when(connection.getHeaderFields()).thenReturn(headers);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(response).setResponseHeaders(headers);
    }
}
