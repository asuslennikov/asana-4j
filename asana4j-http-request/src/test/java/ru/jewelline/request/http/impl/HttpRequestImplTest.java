package ru.jewelline.request.http.impl;

import org.assertj.core.data.MapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestImplTest {

    @Mock
    private HttpRequestFactoryImpl httpRequestFactory;

    private HttpRequestImpl testInstance() {
        return new HttpRequestImpl(this.httpRequestFactory, HttpMethod.PUT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithNullHttpMethod() {
        new HttpRequestImpl(this.httpRequestFactory, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithNullHttpClient() {
        new HttpRequestImpl(null, HttpMethod.POST);
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
        when(this.httpRequestFactory.execute(eq(httpRequest), any(HttpResponseImpl.class))).thenReturn(mock);
        httpRequest.send();
        verify(this.httpRequestFactory).execute(eq(httpRequest), any(HttpResponseImpl.class));
    }

    @Test
    public void test_sendWithNullDestination() {
        HttpRequestImpl httpRequest = testInstance();
        HttpResponse mock = mock(HttpResponse.class);
        when(this.httpRequestFactory.execute(eq(httpRequest), any(HttpResponseImpl.class))).thenReturn(mock);
        HttpResponse response = httpRequest.execute(null);
        assertThat(response).isNotNull();
        verify(this.httpRequestFactory).execute(eq(httpRequest), any(HttpResponseImpl.class));
    }

    @Test
    public void test_sendWithDestination() {
        HttpRequestImpl httpRequest = testInstance();
        HttpResponse mock = mock(HttpResponse.class);
        when(this.httpRequestFactory.execute(eq(httpRequest), any(HttpResponseImpl.class))).thenReturn(mock);
        OutputStream destinationStream = new ByteArrayOutputStream();
        HttpResponse response = httpRequest.execute(destinationStream);
        assertThat(response).isNotNull();
        verify(this.httpRequestFactory).execute(eq(httpRequest), any(HttpResponseImpl.class));
    }
}
