package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.entity.JsonEntity;
import ru.jewelline.asana4j.api.entity.SerializableEntity;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.core.impl.api.entity.StatefulJsonEntity;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestBuilderImpl<T extends JsonEntity<T>> implements ApiRequestBuilder<T> {
    protected static final String BASE_API_URL = "https://app.asana.com/api/1.0/";
    private final ApiEntityInstanceProvider<T> apiInstanceProvider;
    private final HttpClient httpClient;

    private String apiSuffix;
    private Map<String, String> headers;
    private Map<String, String> queryParameters;
    private SerializableEntity entity;

    public ApiRequestBuilderImpl(HttpClient httpClient, ApiEntityInstanceProvider<T> apiInstanceProvider) {
        this.httpClient = httpClient;
        this.apiInstanceProvider = apiInstanceProvider;
    }

    @Override
    public ApiRequestBuilder<T> path(String apiSuffix) {
        this.apiSuffix = apiSuffix;
        return this;
    }

    @Override
    public String getPath() {
        return this.apiSuffix;
    }

    @Override
    public ApiRequestBuilder<T> setQueryParameter(String parameterKey, String parameterValue) {
        if (this.queryParameters == null) {
            this.queryParameters = new HashMap<>();
        }
        this.queryParameters.put(parameterKey, parameterValue);
        return this;
    }

    @Override
    public Map<String, String> getQueryParameters() {
        return this.queryParameters == null ? Collections.<String, String>emptyMap() : Collections.unmodifiableMap(this.queryParameters);
    }

    @Override
    public ApiRequestBuilder<T> setHeader(String headerKey, String headerValue) {
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
    public ApiRequestBuilder<T> setEntity(SerializableEntity entity) {
        if (entity!= null && entity instanceof JsonEntity) {
            this.entity = new StatefulJsonEntity((JsonEntity) entity);
        } else {
            this.entity = entity;
        }
        return this;
    }

    @Override
    public SerializableEntity getEntity() {
        return this.entity;
    }

    @Override
    public ApiRequest<T> buildAs(HttpMethod method) {
        HttpRequestBuilder builder = this.httpClient.newRequest();
        if (this.apiSuffix != null) {
            builder.path(BASE_API_URL + this.apiSuffix);
        } else {
            builder.path(BASE_API_URL);
        }
        if (this.headers != null && this.headers.size() > 0) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                builder.setHeader(header.getKey(), header.getValue());
            }
        }
        if (this.queryParameters != null && this.queryParameters.size() > 0) {
            for (Map.Entry<String, String> header : queryParameters.entrySet()) {
                builder.setQueryParameter(header.getKey(), header.getValue());
            }
        }
        if (this.entity != null) {
            builder.entity(this.entity.getSerialized());
        }
        HttpRequest httpRequest = builder.buildAs(method);
        return new ApiRequestImpl<>(httpRequest, this.apiInstanceProvider);
    }
}
