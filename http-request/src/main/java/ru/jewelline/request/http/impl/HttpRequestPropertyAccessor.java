package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.entity.SerializableEntity;

import java.util.ArrayList;
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

    private boolean isEmptyArray(String[] array){
        return array == null || array.length == 0 || (array.length == 1 && array[0] == null);
    }

    private List<String> unmodifiableListCopy(String[] array){
        if (array.length == 1){
            return Collections.singletonList(array[0]);
        } else {
            List<String> container = new ArrayList<>(array.length);
            for (String elem : array) {
                if (elem != null){
                    container.add(elem);
                }
            }
            return Collections.unmodifiableList(container);
        }
    }

    public void setQueryParameter(String parameterKey, String... parameterValues) {
        if (parameterKey != null) {
            if (isEmptyArray(parameterValues) && this.queryParameters != null) {
                this.queryParameters.remove(parameterKey);
            } else if (!isEmptyArray(parameterValues)) {
                if (this.queryParameters == null) {
                    this.queryParameters = new HashMap<>();
                }
                this.queryParameters.put(parameterKey, unmodifiableListCopy(parameterValues));
            }
        }
    }

    public Map<String, List<String>> getQueryParameters() {
        return this.queryParameters == null
                ? Collections.<String, List<String>>emptyMap()
                : Collections.unmodifiableMap(this.queryParameters);
    }

    public void setHeader(String headerKey, String... headerValues) {
        if (headerKey != null) {
            if (isEmptyArray(headerValues) && this.headers != null) {
                this.headers.remove(headerKey);
            } else if (!isEmptyArray(headerValues)) {
                if (this.headers == null) {
                    this.headers = new HashMap<>();
                }
                this.headers.put(headerKey, unmodifiableListCopy(headerValues));
            }
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
        if (this.headers != null) {
            inst.headers = new HashMap<>(this.headers);
        }
        if (this.queryParameters != null) {
            inst.queryParameters = new HashMap<>(this.queryParameters);
        }
        inst.entity = this.entity;
        return inst;
    }
}
