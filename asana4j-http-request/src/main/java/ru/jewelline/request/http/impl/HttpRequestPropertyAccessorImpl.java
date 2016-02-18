package ru.jewelline.request.http.impl;

import ru.jewelline.HttpRequestPropertyAccessor;
import ru.jewelline.SerializableEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

class HttpRequestPropertyAccessorImpl implements HttpRequestPropertyAccessor {
    protected String url;
    protected Map<String, List<String>> queryParameters;
    protected Map<String, List<String>> headers;
    protected SerializableEntity entity;

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public Map<String, List<String>> getQueryParameters() {
        return this.queryParameters == null
                ? Collections.<String, List<String>>emptyMap()
                : Collections.unmodifiableMap(this.queryParameters);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.headers == null
                ? Collections.<String, List<String>>emptyMap()
                : Collections.unmodifiableMap(this.headers);
    }

    @Override
    public SerializableEntity getEntity() {
        return this.entity;
    }
}
