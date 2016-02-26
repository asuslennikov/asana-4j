package ru.jewelline.asana4j.core.impl.http;

import org.assertj.core.data.MapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.request.http.HttpMethod;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestImplTest {

    @Mock
    private HttpClientImpl httpClient;

    private HttpRequestImpl testInstance() {
        return new HttpRequestImpl(HttpMethod.PUT, this.httpClient);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithNullHttpMethod() {
        new HttpRequestImpl(null, this.httpClient);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithNullHttpClient() {
        new HttpRequestImpl(HttpMethod.POST, null);
    }

    @Test
    public void test_normalCreation() {
        testInstance();

        // assert no exception
    }

    @Test
    public void test_setAndGetNullUrl() {
        HttpRequestImpl httpRequest = testInstance();
        String url = null;
        httpRequest.setUrl(url);
        assertThat(httpRequest.getUrl()).isEqualTo(url);
    }

    @Test
    public void test_setAndGetUrl() {
        HttpRequestImpl httpRequest = testInstance();
        String url = "some url";
        httpRequest.setUrl(url);
        assertThat(httpRequest.getUrl()).isEqualTo(url);
    }

    @Test
    public void test_setAndGetNullHeaders() {
        HttpRequestImpl httpRequest = testInstance();
        httpRequest.setHeaders(null);
        assertThat(httpRequest.getHeaders()).isEmpty();

    }

    @Test
    public void test_setAndGetHeaders() {
        HttpRequestImpl httpRequest = testInstance();
        String hKey = "key";
        String hValue = "value";
        HashMap<String, String> headers = new HashMap<>();
        headers.put(hKey, hValue);
        httpRequest.setHeaders(headers);
        assertThat(httpRequest.getHeaders()).hasSize(1);
        assertThat(httpRequest.getHeaders()).containsOnly(MapEntry.entry(hKey, hValue));
    }

    @Test
    public void test_setHeadersAndAddingToMapHasNoEffect() {
        HttpRequestImpl httpRequest = testInstance();
        String hKey = "key";
        String hValue = "value";
        HashMap<String, String> headers = new HashMap<>();
        headers.put(hKey, hValue);
        httpRequest.setHeaders(headers);
        headers.put("key2", "value2");
        assertThat(httpRequest.getHeaders()).hasSize(1);
        assertThat(httpRequest.getHeaders()).containsOnly(MapEntry.entry(hKey, hValue));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_setAndGetHeadersAddingToMapHasNoEffect() {
        HttpRequestImpl httpRequest = testInstance();
        String hKey = "key";
        String hValue = "value";
        HashMap<String, String> headers = new HashMap<>();
        headers.put(hKey, hValue);
        httpRequest.setHeaders(headers);
        httpRequest.getHeaders().put("key2", "value2");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_setAndGetEmptyHeadersAddingToMapHasNoEffect() {
        HttpRequestImpl httpRequest = testInstance();
        HashMap<String, String> headers = new HashMap<>();
        httpRequest.setHeaders(headers);
        httpRequest.getHeaders().put("key2", "value2");
    }

    @Test
    public void test_getHttpMethod() {
        HttpRequestImpl httpRequest = testInstance();
        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    public void test_setAndGetNullEntity() {
        HttpRequestImpl httpRequest = testInstance();
        httpRequest.setEntity(null);
        assertThat(httpRequest.getEntity()).isNull();
    }

    @Test
    public void test_setAndGetEntity() {
        HttpRequestImpl httpRequest = testInstance();
        ByteArrayInputStream entity = new ByteArrayInputStream(new byte[]{1, 2, 3});
        httpRequest.setEntity(entity);
        assertThat(httpRequest.getEntity()).isEqualTo(entity);
    }

    @Test
    public void test_send() {
        HttpRequestImpl httpRequest = testInstance();
        HttpResponse mock = mock(HttpResponse.class);
        when(this.httpClient.execute(eq(httpRequest), any(HttpResponseImpl.class))).thenReturn(mock);
        HttpResponse<OutputStream> response = httpRequest.send();
        assertThat(response).isNotNull();
        verify(this.httpClient).execute(eq(httpRequest), any(HttpResponseImpl.class));
    }

    @Test
    public void test_sendWithNullDestination() {
        HttpRequestImpl httpRequest = testInstance();
        HttpResponse mock = mock(HttpResponse.class);
        when(this.httpClient.execute(eq(httpRequest), any(HttpResponseImpl.class))).thenReturn(mock);
        HttpResponse<OutputStream> response = httpRequest.sendAndReadResponse(null);
        assertThat(response).isNotNull();
        verify(this.httpClient).execute(eq(httpRequest), any(HttpResponseImpl.class));
    }

    @Test
    public void test_sendWithDestination() {
        HttpRequestImpl httpRequest = testInstance();
        HttpResponse mock = mock(HttpResponse.class);
        when(this.httpClient.execute(eq(httpRequest), any(HttpResponseImpl.class))).thenReturn(mock);
        OutputStream destinationStream = new ByteArrayOutputStream();
        HttpResponse<OutputStream> response = httpRequest.sendAndReadResponse(destinationStream);
        assertThat(response).isNotNull();
        verify(this.httpClient).execute(eq(httpRequest), any(HttpResponseImpl.class));
    }
}
