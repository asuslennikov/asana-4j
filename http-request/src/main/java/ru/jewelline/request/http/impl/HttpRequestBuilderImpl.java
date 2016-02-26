package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.entity.SerializableEntity;
import ru.jewelline.request.http.entity.StreamBasedEntity;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

class HttpRequestBuilderImpl implements HttpRequestBuilder {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private final HttpRequestFactory httpRequestFactory;
    private HttpRequestPropertyAccessor propertyAccessor;
    private AtomicBoolean requirePropertyAccessorClone = new AtomicBoolean(false);

    HttpRequestBuilderImpl(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
        this.propertyAccessor = new HttpRequestPropertyAccessor();
    }

    private void ensureActualPropertyAccessorLoaded() {
        if (this.requirePropertyAccessorClone.get()) {
            try {
                this.propertyAccessor = (HttpRequestPropertyAccessor) this.propertyAccessor.clone();
            } catch (CloneNotSupportedException e) {
                // HttpRequestPropertyAccessor implements cloneable
            } finally {
                this.requirePropertyAccessorClone.compareAndSet(true, false);
            }
        }
    }

    @Override
    public HttpRequestBuilder setUrl(String url) {
        ensureActualPropertyAccessorLoaded();
        if (url == null || url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX)) {
            this.propertyAccessor.setUrl(url);
        } else {
            this.propertyAccessor.setUrl(HTTP_PREFIX + url);
        }
        return this;
    }

    @Override
    public String getUrl() {
        return this.propertyAccessor.getUrl();
    }

    @Override
    public HttpRequestBuilder setQueryParameter(String parameterKey, String... parameterValues) {
        ensureActualPropertyAccessorLoaded();
        this.propertyAccessor.setQueryParameter(parameterKey, parameterValues);
        return this;
    }

    @Override
    public Map<String, List<String>> getQueryParameters() {
        return this.propertyAccessor.getQueryParameters();
    }

    @Override
    public HttpRequestBuilder setHeader(String headerKey, String... headerValues) {
        ensureActualPropertyAccessorLoaded();
        this.propertyAccessor.setHeader(headerKey, headerValues);
        return this;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.propertyAccessor.getHeaders();
    }

    @Override
    public HttpRequestBuilder setEntity(InputStream entityStream) {
        ensureActualPropertyAccessorLoaded();
        this.propertyAccessor.setEntity(new StreamBasedEntity(entityStream));
        return this;
    }

    @Override
    public HttpRequestBuilder setEntity(SerializableEntity entity) {
        ensureActualPropertyAccessorLoaded();
        this.propertyAccessor.setEntity(entity);
        return this;
    }

    @Override
    public SerializableEntity getEntity() {
        return this.propertyAccessor.getEntity();
    }

    @Override
    public HttpRequest buildAs(HttpMethod method) {
        if (method == null) {
            throw new NetworkException(NetworkException.MALFORMED_URL, "You must specify a request method");
        }
        HttpRequestPropertyAccessor propertyAccessor = this.propertyAccessor;
        this.requirePropertyAccessorClone.set(true);
        return new HttpRequestImpl(this.httpRequestFactory, propertyAccessor, method);
    }
}
