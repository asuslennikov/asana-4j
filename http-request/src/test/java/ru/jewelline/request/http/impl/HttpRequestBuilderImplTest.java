package ru.jewelline.request.http.impl;

import org.assertj.core.data.MapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.UrlBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestBuilderImplTest {

    @Mock
    private UrlBuilder urlBuilder;
    @Mock
    private HttpRequestFactoryImpl httpRequestFactory;

    private HttpRequestBuilderImpl getRequestBuilder() {
        when(httpRequestFactory.urlBuilder()).thenReturn(urlBuilder);
        return new HttpRequestBuilderImpl(this.httpRequestFactory);
    }

    @Test
    public void test_addDefaultTransportPrefix() {
        String path = "www.example.com";
        HttpRequestBuilderImpl requestBuilder = getRequestBuilder();
        requestBuilder.setUrl(path);
        assertThat(requestBuilder.getUrl()).isEqualTo("http://" + path);
    }

    @Test
    public void test_notAppendDefaultTransportPrefixForHttp() {
        String path = "http://www.example.com";
        HttpRequestBuilderImpl requestBuilder = getRequestBuilder();
        requestBuilder.setUrl(path);
        assertThat(requestBuilder.getUrl()).isEqualTo(path);
    }

    @Test
    public void test_notAppendDefaultTransportPrefixForHttps() {
        String path = "https://www.example.com";
        HttpRequestBuilderImpl requestBuilder = getRequestBuilder();
        requestBuilder.setUrl(path);
        assertThat(requestBuilder.getUrl()).isEqualTo(path);
    }

    @Test
    public void test_allowResetPathByNullValue() {
        HttpRequestBuilderImpl requestBuilder = getRequestBuilder();
        requestBuilder.setUrl("non.null");
        requestBuilder.setUrl(null);
        assertThat(requestBuilder.getUrl()).isEqualTo(null);
    }

    @Test
    public void test_propagateQueryParameters() {
        String qpKey = "key";
        String qpValue = "value";
        HttpRequestBuilderImpl requestBuilder = getRequestBuilder();
        requestBuilder.setQueryParameter(qpKey, qpValue);
        assertThat(requestBuilder.getQueryParameters()).hasSize(1).contains(MapEntry.entry(qpKey, Collections.singletonList(qpValue)));
    }

    @Test
    public void test_propagateQueryParametersKeyNull() {
        String qpKey = null;
        String qpValue = "value";
        HttpRequestBuilderImpl requestBuilder = getRequestBuilder();
        requestBuilder.setQueryParameter(qpKey, qpValue);
        assertThat(requestBuilder.getQueryParameters()).hasSize(1).contains(MapEntry.entry(qpKey, Collections.singletonList(qpValue)));
    }

    @Test
    public void test_propagateQueryParametersValueNull() {
        String qpKey = "key";
        String qpValue = null;
        HttpRequestBuilderImpl requestBuilder = getRequestBuilder();
        requestBuilder.setQueryParameter(qpKey, qpValue);
        assertThat(requestBuilder.getQueryParameters()).hasSize(1).contains(MapEntry.entry(qpKey, Collections.singletonList(qpValue)));
    }

    @Test
    public void test_canAddHeaders() {
        getRequestBuilder()
                .setHeader("key1", "value1")
                .setHeader("key2", "value2");

        // assert no exceptions
    }

    @Test
    public void test_setNullStreamAsPayload() {
        InputStream payload = null;
        getRequestBuilder().setEntity(payload);

        // assert no exception
    }

    @Test
    public void test_setStreamAsPayload() {
        InputStream payload = new ByteArrayInputStream(new byte[]{});
        getRequestBuilder().setEntity(payload);

        // assert no exception
    }

    @Test
    public void test_throwsExceptionForMissedHttpMethod() {
        try {
            getRequestBuilder().buildAs(null);
            fail("The NetworkException should be thrown since there is no base url");
        } catch (NetworkException ex) {
            assertThat(ex.getErrorCode()).isEqualTo(NetworkException.MALFORMED_URL);
        }
    }

    @Test
    public void test_throwsExceptionForMissedBaseUrl() {
        try {
            getRequestBuilder().buildAs(HttpMethod.GET);
            fail("The NetworkException should be thrown since there is no base url");
        } catch (NetworkException ex) {
            assertThat(ex.getErrorCode()).isEqualTo(NetworkException.MALFORMED_URL);
        }
    }

    @Test
    public void test_throwsExceptionForBadBaseUrl() {
        String path = "www.example.com";
        when(urlBuilder.build()).thenReturn(path);
        try {
            getRequestBuilder()
                    .setUrl(path)
                    .buildAs(HttpMethod.GET);
            fail("The NetworkException should be thrown since there is no base url");
        } catch (NetworkException ex) {
            assertThat(ex.getErrorCode()).isEqualTo(NetworkException.MALFORMED_URL);
        }
    }

    @Test
    public void test_buildRequestJustWithPathAndMethod() {
        String path = "http://www.example.com";
        HttpMethod httpMethod = HttpMethod.GET;
        when(urlBuilder.build()).thenReturn(path);
        HttpRequest httpRequest = getRequestBuilder()
                .setUrl(path)
                .buildAs(httpMethod);

        assertThat(httpRequest.getUrl()).isEqualTo(path);
        assertThat(httpRequest.getMethod()).isEqualTo(httpMethod);
        verify(urlBuilder).build();
    }

    @Test
    public void test_buildRequestWithPathMethodAndQueryParameters() {
        String path = "http://www.example.com";
        String pathWithParams = path + "?key=value";
        HttpMethod httpMethod = HttpMethod.GET;
        when(urlBuilder.build()).thenReturn(pathWithParams);
        HttpRequest httpRequest = getRequestBuilder()
                .setUrl(path)
                .setQueryParameter("key", "value")
                .buildAs(httpMethod);

        assertThat(httpRequest.getUrl()).isEqualTo(pathWithParams);
        assertThat(httpRequest.getMethod()).isEqualTo(httpMethod);
        verify(urlBuilder).build();
    }

    @Test
    public void test_buildRequestWithPathMethodAndHeaders() {
        String path = "http://www.example.com";
        HttpMethod httpMethod = HttpMethod.GET;
        when(urlBuilder.build()).thenReturn(path);
        HttpRequest httpRequest = getRequestBuilder()
                .setUrl(path)
                .setHeader("key", "value")
                .buildAs(httpMethod);

        assertThat(httpRequest.getUrl()).isEqualTo(path);
        assertThat(httpRequest.getMethod()).isEqualTo(httpMethod);
        assertThat(httpRequest.getHeaders()).isNotEmpty();
        verify(urlBuilder).build();
    }

    @Test
    public void test_buildRequestWithDublicateHeaders() {
        String path = "http://www.example.com";
        HttpMethod httpMethod = HttpMethod.GET;
        when(urlBuilder.build()).thenReturn(path);
        HttpRequest httpRequest = getRequestBuilder()
                .setUrl(path)
                .setHeader("key", "value1")
                .setHeader("key", "value2")
                .buildAs(httpMethod);

        assertThat(httpRequest.getUrl()).isEqualTo(path);
        assertThat(httpRequest.getMethod()).isEqualTo(httpMethod);
        assertThat(httpRequest.getHeaders()).hasSize(1);
        assertThat(httpRequest.getHeaders()).containsValue(Collections.singletonList("value2"));
        verify(urlBuilder).build();
    }

    @Test
    public void test_buildRequestWithDifferentHeaders() {
        String path = "http://www.example.com";
        HttpMethod httpMethod = HttpMethod.GET;
        when(urlBuilder.build()).thenReturn(path);
        HttpRequest httpRequest = getRequestBuilder()
                .setUrl(path)
                .setHeader("key1", "value")
                .setHeader("key2", "value")
                .buildAs(httpMethod);

        assertThat(httpRequest.getUrl()).isEqualTo(path);
        assertThat(httpRequest.getMethod()).isEqualTo(httpMethod);
        assertThat(httpRequest.getHeaders()).hasSize(2);
        verify(urlBuilder).build();
    }

    @Test
    public void test_buildRequestWithByteEntity() {
        String path = "http://www.example.com";
        HttpMethod httpMethod = HttpMethod.GET;
        byte[] payload = {1, 2, 3};
        when(urlBuilder.build()).thenReturn(path);

        HttpRequest httpRequest = getRequestBuilder()
                .setUrl(path)
                .setEntity(new ByteArrayInputStream(payload))
                .buildAs(httpMethod);

        assertThat(httpRequest.getUrl()).isEqualTo(path);
        assertThat(httpRequest.getMethod()).isEqualTo(httpMethod);
        assertThat(httpRequest.getEntity()).isNotNull();
        verify(urlBuilder).build();
    }

    @Test
    public void test_buildRequestWithStreamEntity() {
        String path = "http://www.example.com";
        HttpMethod httpMethod = HttpMethod.GET;
        InputStream payload = new ByteArrayInputStream(new byte[]{1, 2, 3});
        when(urlBuilder.build()).thenReturn(path);

        HttpRequest httpRequest = getRequestBuilder()
                .setUrl(path)
                .setEntity(payload)
                .buildAs(httpMethod);

        assertThat(httpRequest.getUrl()).isEqualTo(path);
        assertThat(httpRequest.getMethod()).isEqualTo(httpMethod);
        assertThat(httpRequest.getEntity()).isEqualTo(payload);
        verify(urlBuilder).build();
    }
}
