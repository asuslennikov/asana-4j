package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.utils.URLCreator;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.NetworkException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilderImpl implements HttpRequestBuilder {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private final HttpRequestFactoryImpl httpClient;

    private URLCreator.Builder urlBuilder;
    private Map<String, String> headers;
    private InputStream entityStream;

    HttpRequestBuilderImpl(URLCreator urlCreator, HttpRequestFactoryImpl httpClient) {
        this.headers = new HashMap<>();
        this.urlBuilder = urlCreator.builder();
        this.httpClient = httpClient;
    }

    @Override
    public HttpRequestBuilder path(String url) {
        if (url == null || url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX)) {
            this.urlBuilder.path(url);
        } else {
            this.urlBuilder.path(HTTP_PREFIX + url);
        }
        return this;
    }

    @Override
    public HttpRequestBuilder setQueryParameter(String parameterKey, String parameterValue) {
        this.urlBuilder.addQueryParameter(parameterKey, parameterValue);
        return this;
    }

    @Override
    public HttpRequestBuilder setHeader(String headerKey, String headerValue) {
        this.headers.put(headerKey, headerValue);
        return this;
    }

    @Override
    public HttpRequestBuilder entity(byte[] requestBody) {
        return this.entity(requestBody != null ? new ByteArrayInputStream(requestBody) : (InputStream) null);
    }

    @Override
    public HttpRequestBuilder entity(InputStream entityStream) {
        this.entityStream = entityStream;
        return this;
    }

    @Override
    public HttpRequest buildAs(HttpMethod method) {
        if (method == null) {
            throw new NetworkException(NetworkException.MALFORMED_URL, "You must specify a request method");
        }
        String url = this.urlBuilder.build();
        if (url == null || !url.startsWith(HTTP_PREFIX) && !url.startsWith(HTTPS_PREFIX)) {
            throw new NetworkException(NetworkException.MALFORMED_URL, "You must specify a url for your request");
        }
        HttpRequestImpl request = new HttpRequestImpl(method, this.httpClient);
        request.setUrl(url);
        request.setHeaders(this.headers);
        request.setEntity(this.entityStream);
        return request;
    }
}
