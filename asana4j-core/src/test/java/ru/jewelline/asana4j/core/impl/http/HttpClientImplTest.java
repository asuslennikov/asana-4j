package ru.jewelline.asana4j.core.impl.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.core.impl.http.config.BaseHttpConfiguration;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.NetworkException;
import ru.jewelline.asana4j.utils.URLCreator;
import ru.jewelline.request.http.HttpMethod;

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
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpClientImplTest {

    @Mock
    private URLCreator urlCreator;
    @Mock
    private HttpURLConnection connection;
    private BaseHttpConfiguration httpConfig = new BaseHttpConfiguration();

    @Test
    public void test_copyNullSourceToDestination() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        HttpClientImpl.copyStreams(null, outputStream);
        verifyZeroInteractions(outputStream);
    }

    @Test
    public void test_copySourceToNull() throws IOException {
        InputStream source = mock(InputStream.class);
        HttpClientImpl.copyStreams(source, null);
        verifyZeroInteractions(source);
    }

    @Test
    public void test_correctCopyStreams() throws IOException {
        byte[] payload = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        ByteArrayInputStream source = new ByteArrayInputStream(payload);
        ByteArrayOutputStream destination = new ByteArrayOutputStream();
        HttpClientImpl.copyStreams(source, destination);
        assertThat(destination.toByteArray()).isEqualTo(payload);
    }

    @Test
    public void test_noExceptionOnClose() throws IOException {
        byte[] payload = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        InputStream source = new ByteArrayInputStream(payload);
        OutputStream destination = mock(OutputStream.class);
        doThrow(new IOException("test")).when(destination).close();
        HttpClientImpl.copyStreams(source, destination);

        verify(destination).flush();
        //assert no exception
    }

    private HttpClientImpl testInstance() {
        return new HttpClientImpl(this.urlCreator, this.httpConfig) {
            @Override
            protected HttpURLConnection createConnection(HttpRequest request) throws IOException {
                return HttpClientImplTest.this.connection;
            }
        };
    }

    @Test
    public void test_clientCanReturnNewBuilderInstance() {
        HttpClientImpl httpClient = testInstance();
        assertThat(httpClient.newRequest() != httpClient.newRequest()).isTrue();
    }

    @Test(expected = NetworkException.class)
    public void test_executeWithNullResponse() {
        testInstance().execute(mock(HttpRequestImpl.class), null);
    }

    @Test(expected = NetworkException.class)
    public void test_executeWithNullRequest() {
        testInstance().execute(null, mock(HttpResponseImpl.class));
    }

    @Test
    public void test_returnsTheSameResponse() {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);
        assertThat(response == testInstance().execute(request, response)).isTrue();
    }

    @Test
    public void test_configureConnectionWithoutHeaders() {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);
        when(request.getHeaders()).thenReturn(Collections.<String, String>emptyMap());

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).setConnectTimeout(Matchers.anyInt());
        verify(connection, never()).setRequestProperty(anyString(), anyString());
    }

    @Test
    public void test_configureConnectionWithHeaders() {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);
        Map<String, String> headers = new HashMap<>();
        headers.put("key1", "value1");
        headers.put("key2", "value2");
        when(request.getHeaders()).thenReturn(headers);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).setConnectTimeout(Matchers.anyInt());
        verify(connection).setRequestProperty("key1", "value1");
        verify(connection).setRequestProperty("key2", "value2");
    }

    @Test
    public void test_configureConnectionNoInput() {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        when(response.output()).thenReturn(null);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).setDoInput(true);
        verify(connection).setConnectTimeout(Matchers.anyInt());
    }

    @Test
    public void test_configureConnectionWithInput() {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        OutputStream outputStream = mock(OutputStream.class);
        when(response.output()).thenReturn(outputStream);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).setDoInput(true);
        verify(connection).setConnectTimeout(Matchers.anyInt());
    }

    @Test
    public void test_verifySetResponseCode() throws IOException {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        OutputStream outputStream = mock(OutputStream.class);
        when(response.output()).thenReturn(outputStream);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);
        int responseCode = 200;
        when(connection.getResponseCode()).thenReturn(responseCode);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).getResponseCode();
        verify(response).setCode(responseCode);
    }

    @Test
    public void test_pickDataStreamForGoodCode() throws IOException {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        OutputStream outputStream = mock(OutputStream.class);
        when(response.output()).thenReturn(outputStream);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        int responseCode = 201;
        InputStream inputStream = mock(InputStream.class);
        when(connection.getInputStream()).thenReturn(inputStream);
        when(connection.getResponseCode()).thenReturn(responseCode);
        when(response.code()).thenReturn(responseCode);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection).getInputStream();
        verify(outputStream).flush(); // verify that stream was copied
    }

    @Test
    public void test_pickErrorStreamForBadCode() throws IOException {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        OutputStream outputStream = mock(OutputStream.class);
        when(response.output()).thenReturn(outputStream);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        int responseCode = 400;
        when(response.code()).thenReturn(responseCode);
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
    public void test_getNoStreamForGoodCodeAndNoOutput() throws IOException {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        int responseCode = 201;
        when(response.code()).thenReturn(responseCode);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection, never()).getInputStream();
    }

    @Test
    public void test_getNoStreamForBadCodeAndNoOutput() throws IOException {
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        int responseCode = 400;
        when(response.code()).thenReturn(responseCode);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(connection, never()).getInputStream();
    }

    @Test
    public void test_doAdditionalAttemptsInCaseOfNoResponseCode(){
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        int responseCode = -1;
        when(response.code()).thenReturn(responseCode);

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
        HttpResponseImpl response = mock(HttpResponseImpl.class);
        HttpRequestImpl request = mock(HttpRequestImpl.class);
        when(request.getMethod()).thenReturn(HttpMethod.PUT);
        int responseCode = 201;
        when(response.code()).thenReturn(responseCode);
        when(connection.getHeaderFields()).thenReturn(headers);

        // business method
        testInstance().execute(request, response);

        // assertions
        verify(response).setHeaders(headers);
    }
}
