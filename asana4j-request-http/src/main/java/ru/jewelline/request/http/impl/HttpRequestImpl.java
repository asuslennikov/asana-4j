package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestImpl implements HttpRequest {

    private final HttpMethod httpMethod;
    private final HttpRequestFactoryImpl httpClient;

    private String url;
    private Map<String, String> headers;
    private InputStream entity;

    public HttpRequestImpl(HttpMethod httpMethod, HttpRequestFactoryImpl httpClient) {
        if (httpMethod == null) {
            throw new IllegalArgumentException("Http method can not be null");
        }
        if (httpClient == null) {
            throw new IllegalArgumentException("Http client can not be null");
        }
        this.httpMethod = httpMethod;
        this.httpClient = httpClient;
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
        this.sendAndReadResponse(null);
    }

    @Override
    public <T extends OutputStream> HttpResponse<T> sendAndReadResponse(T destinationStream) {
        return this.httpClient.execute(this, new HttpResponseImpl<>(destinationStream));
    }
}
