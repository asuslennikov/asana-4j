package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.entity.SerializableEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class HttpRequestPropertyAccessor implements Cloneable {
    private String url;
    private Map<String, List<String>> queryParameters;
    private Map<String, List<String>> headers;
    private SerializableEntity entity;

    HttpRequestPropertyAccessor() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setQueryParameter(String parameterKey, String... parameterValues) {
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
    }

    public Map<String, List<String>> getQueryParameters() {
        return this.queryParameters == null
                ? Collections.<String, List<String>>emptyMap()
                : Collections.unmodifiableMap(this.queryParameters);
    }

    public void setHeader(String headerKey, String... headerValues) {
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
    }

    public Map<String, List<String>> getHeaders() {
        return this.headers == null
                ? Collections.<String, List<String>>emptyMap()
                : Collections.unmodifiableMap(this.headers);
    }

    public SerializableEntity getEntity() {
        return this.entity;
    }

    public void setEntity(SerializableEntity entity) {
        this.entity = entity;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        HttpRequestPropertyAccessor inst = (HttpRequestPropertyAccessor) super.clone();
        inst.url = this.url;
        inst.headers = new HashMap<>(this.headers);
        inst.queryParameters = new HashMap<>(this.queryParameters);
        inst.entity = this.entity;
        return inst;
    }
}
