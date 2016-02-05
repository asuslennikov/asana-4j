package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.UrlProvider;
import ru.jewelline.request.http.entity.EntitySerializer;
import ru.jewelline.request.http.entity.SerializableEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class HttpRequestBuilderImpl implements HttpRequestBuilder {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private final HttpRequestFactoryImpl httpRequestFactory;
    private final UrlProvider.Builder urlBuilder;

    private String path;
    private Map<String, String> queryParameters;
    private Map<String, String> headers;
    private SerializableEntity entity;

    HttpRequestBuilderImpl(HttpRequestFactoryImpl httpRequestFactory, UrlProvider urlProvider) {
        this.urlBuilder = urlProvider.newBuilder();
        this.httpRequestFactory = httpRequestFactory;
    }

    @Override
    public HttpRequestBuilder path(String url) {
        if (url == null || url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX)) {
            this.path = url;
        } else {
            this.path = HTTP_PREFIX + url;
        }
        return this;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public HttpRequestBuilder setQueryParameter(String parameterKey, String parameterValue) {
        if (this.queryParameters == null) {
            this.queryParameters = new HashMap<>();
        }
        this.queryParameters.put(parameterKey, parameterValue);
        return this;
    }

    @Override
    public Map<String, String> getQueryParameters() {
        return this.queryParameters == null
                ? Collections.<String, String>emptyMap()
                : Collections.unmodifiableMap(this.queryParameters);
    }

    @Override
    public HttpRequestBuilder setHeader(String headerKey, String headerValue) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.put(headerKey, headerValue);
        return this;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers == null ? Collections.<String, String>emptyMap() : Collections.unmodifiableMap(this.headers);
    }

    @Override
    public HttpRequestBuilder setEntity(byte[] requestBody) {
        return this.setEntity(requestBody != null ? new ByteArrayInputStream(requestBody) : (InputStream) null);
    }

    @Override
    public HttpRequestBuilder setEntity(InputStream entityStream) {
        this.entity = new SerializedEntityHolder(entityStream);
        return this;
    }

    @Override
    public <T> HttpRequestBuilder setEntity(T entity, EntitySerializer<T> serializer) {
        this.entity = new SerializableEntityWrapper<>(entity, serializer);
        return this;
    }

    @Override
    public HttpRequestBuilder setEntity(SerializableEntity entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public SerializableEntity getEntity() {
        return this.entity;
    }

    @Override
    public HttpRequest buildAs(HttpMethod method) {
        if (method == null) {
            throw new NetworkException(NetworkException.MALFORMED_URL, "You must specify a request method");
        }
        this.urlBuilder.path(this.path);
        if (this.queryParameters != null && this.queryParameters.size() > 0) {
            for (Map.Entry<String, String> header : this.queryParameters.entrySet()) {
                this.urlBuilder.addQueryParameter(header.getKey(), header.getValue());
            }
        }
        String url = this.urlBuilder.build();
        if (url == null || !url.startsWith(HTTP_PREFIX) && !url.startsWith(HTTPS_PREFIX)) {
            throw new NetworkException(NetworkException.MALFORMED_URL, "You must specify a url for your request");
        }
        HttpRequestImpl request = new HttpRequestImpl(this.httpRequestFactory, method);
        request.setUrl(url);
        request.setHeaders(this.headers);
        if (this.entity != null) {
            request.setEntity(this.entity.getSerializer().serialize(this.entity));
        }
        return request;
    }
}
