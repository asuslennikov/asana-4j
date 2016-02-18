package ru.jewelline.request.http.impl;

import ru.jewelline.HttpRequest;
import ru.jewelline.HttpResponseReceiver;
import ru.jewelline.SerializableEntity;
import ru.jewelline.request.http.HttpMethod;

import java.util.Collections;
import java.util.List;
import java.util.Map;

final class HttpRequestImpl implements HttpRequest {

    private final HttpMethod httpMethod;
    private final HttpRequestFactoryImpl httpRequestFactory;

    private String url;
    private Map<String, List<String>> headers;
    private SerializableEntity entity;

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
    public Map<String, List<String>> getQueryParameters() {
        return null;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.headers == null
                ? Collections.<String, List<String>>emptyMap()
                : Collections.unmodifiableMap(this.headers);
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers != null ? headers : null;
    }

    @Override
    public SerializableEntity getEntity() {
        return this.entity;
    }

    @Override
    public <T extends HttpResponseReceiver> T execute(T responseReceiver) {
        return null;
    }


    public void setEntity(SerializableEntity entityStream) {
        this.entity = entityStream;
    }


}
