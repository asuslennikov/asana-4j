package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.HttpResponseReceiver;
import ru.jewelline.request.http.entity.SerializableEntity;

import java.util.List;
import java.util.Map;

final class HttpRequestImpl implements HttpRequest {

    private final HttpMethod httpMethod;
    private final HttpRequestFactory httpRequestFactory;
    private final HttpRequestPropertyAccessor propertyAccessor;

    HttpRequestImpl(HttpRequestFactory httpRequestFactory, HttpRequestPropertyAccessor propertyAccessor, HttpMethod httpMethod) {
        if (httpMethod == null) {
            throw new IllegalArgumentException("Http method can not be null");
        }
        if (httpRequestFactory == null) {
            throw new IllegalArgumentException("Http client can not be null");
        }
        if (propertyAccessor == null) {
            throw new IllegalArgumentException("Property accessor can not be null");
        }
        this.httpRequestFactory = httpRequestFactory;
        this.propertyAccessor = propertyAccessor;
        this.httpMethod = httpMethod;
    }

    @Override
    public HttpMethod getMethod() {
        return this.httpMethod;
    }

    @Override
    public Map<String, List<String>> getQueryParameters() {
        return this.propertyAccessor.getQueryParameters();
    }

    @Override
    public String getUrl() {
        return this.propertyAccessor.getUrl();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.propertyAccessor.getHeaders();
    }

    @Override
    public SerializableEntity getEntity() {
        return this.propertyAccessor.getEntity();
    }

    @Override
    public <T extends HttpResponseReceiver> T execute(T responseReceiver) {
        this.httpRequestFactory.execute(this, responseReceiver);
        return responseReceiver;
    }
}
