package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class HttpRequestImpl implements HttpRequest {

    private final HttpMethod httpMethod;
    private final HttpRequestFactoryImpl httpRequestFactory;

    private String url;
    private Map<String, String> headers;
    private InputStream entity;

    HttpRequestImpl(HttpRequestFactoryImpl httpRequestFactory, HttpMethod httpMethod) {
        if (httpMethod == null) {
            throw new IllegalArgumentException("Http method can not be null");
        }
        if (httpRequestFactory == null) {
            throw new IllegalArgumentException("Http client can not be null");
        }
        this.httpRequestFactory = httpRequestFactory;
        this.httpMethod = httpMethod;
    }

    @Override
    public HttpMethod getMethod() {
        return this.httpMethod;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers != null ? Collections.unmodifiableMap(this.headers) : Collections.<String, String>emptyMap();
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers != null ? new HashMap<>(headers) : null;
    }

    @Override
    public InputStream getEntity() {
        return this.entity;
    }


    public void setEntity(InputStream entityStream) {
        this.entity = entityStream;
    }

    @Override
    public void send() {
        this.execute(null);
    }

    @Override
    public HttpResponse<?> execute() {
        return execute(new ByteArrayOutputStream(8192));
    }

    @Override
    public <T extends OutputStream> HttpResponse<T> execute(T destinationStream) {
        return this.httpRequestFactory.execute(this, new HttpResponseImpl(destinationStream));
    }
}
