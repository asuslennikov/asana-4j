package ru.jewelline.request.http.impl;

import ru.jewelline.HttpRequest;
import ru.jewelline.HttpRequestBuilder;
import ru.jewelline.SerializableEntity;
import ru.jewelline.StreamBasedEntity;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.NetworkException;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HttpRequestBuilderImpl extends HttpRequestPropertyAccessorImpl implements HttpRequestBuilder {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private final HttpRequestFactoryImpl httpRequestFactory;

    HttpRequestBuilderImpl(HttpRequestFactoryImpl httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }

    @Override
    public HttpRequestBuilder setUrl(String url) {
        if (url == null || url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX)) {
            this.url = url;
        } else {
            this.url = HTTP_PREFIX + url;
        }
        return this;
    }

    @Override
    public HttpRequestBuilder setQueryParameter(String parameterKey, String... parameterValues) {
        if (parameterKey == null) {
            throw new IllegalArgumentException("Query parameter key can not be null.");
        }
        if (parameterValues == null && this.queryParameters != null) {
            this.queryParameters.remove(parameterKey);
        } else {
            if (this.queryParameters == null) {
                this.queryParameters = new HashMap<>();
            }
            this.queryParameters.put(parameterKey, Collections.unmodifiableList(Arrays.asList(parameterValues.clone())));
        }
        return this;
    }

    @Override
    public HttpRequestBuilder setHeader(String headerKey, String... headerValues) {
        if (headerKey == null) {
            throw new IllegalArgumentException("Header key can not be null.");
        }
        if (headerValues == null && this.queryParameters != null) {
            this.queryParameters.remove(headerKey);
        } else {
            if (this.headers == null) {
                this.headers = new HashMap<>();
            }
            this.headers.put(headerKey, Collections.unmodifiableList(Arrays.asList(headerValues.clone())));
        }
        return this;
    }

    @Override
    public HttpRequestBuilder setEntity(InputStream entityStream) {
        this.entity = new StreamBasedEntity(entityStream);
        return this;
    }

    @Override
    public HttpRequestBuilder setEntity(SerializableEntity entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public HttpRequest buildAs(HttpMethod method) {
        if (method == null) {
            throw new NetworkException(NetworkException.MALFORMED_URL, "You must specify a request method");
        }
        this.urlBuilder.path(this.url);
        if (this.queryParameters != null && this.queryParameters.size() > 0) {
            for (Map.Entry<String, List<String>> header : this.queryParameters.entrySet()) {
                for (String queryParameter : header.getValue()) {
                    this.urlBuilder.addQueryParameter(header.getKey(), queryParameter);
                }
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
            request.setEntity(this.entity);
        }
        return request;
    }
}
