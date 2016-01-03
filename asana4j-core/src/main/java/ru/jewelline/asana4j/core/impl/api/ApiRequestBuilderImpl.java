package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.entity.io.EntitySerializer;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;
import ru.jewelline.asana4j.core.impl.api.entity.io.CachedJsonEntity;
import ru.jewelline.asana4j.core.impl.api.entity.io.SerializableEntityImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestBuilderImpl implements ApiRequestBuilder {
    protected static final String BASE_API_URL = "https://app.asana.com/api/1.0/";
    private final HttpClient httpClient;

    private String apiSuffix;
    private Map<String, String> headers;
    private Map<String, String> queryParameters;
    private SerializableEntity entity;

    public ApiRequestBuilderImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public ApiRequestBuilder path(String apiSuffix) {
        if (!StringUtils.emptyOrOnlyWhiteSpace(apiSuffix)){
            if (apiSuffix.startsWith("/")){
                /** skip the first "/" symbol, because we already have it in the {@link BASE_API_URL} */
                this.apiSuffix = apiSuffix.substring(1);
            } else {
                this.apiSuffix = apiSuffix;
            }
        }
        return this;
    }

    @Override
    public String getPath() {
        return this.apiSuffix;
    }

    @Override
    public ApiRequestBuilder setQueryParameter(String parameterKey, String parameterValue) {
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
    public ApiRequestBuilder setHeader(String headerKey, String headerValue) {
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
    public <T> ApiRequestBuilder setEntity(T entity, EntitySerializer<T> serializer) {
        return setEntity(new SerializableEntityImpl<>(serializer, entity));
    }

    @Override
    public ApiRequestBuilder setEntity(SerializableEntity entity) {
        if (entity!= null && entity instanceof JsonEntity && !(entity instanceof CachedJsonEntity)) {
            this.entity = new CachedJsonEntity((JsonEntity) entity);
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
    public ApiRequest buildAs(HttpMethod method) {
        HttpRequestBuilder builder = this.httpClient.newRequest();
        if (this.apiSuffix != null) {
            builder.path(BASE_API_URL + this.apiSuffix);
        } else {
            builder.path(BASE_API_URL);
        }
        if (this.headers != null && this.headers.size() > 0) {
            for (Map.Entry<String, String> header : this.headers.entrySet()) {
                builder.setHeader(header.getKey(), header.getValue());
            }
        }
        if (this.queryParameters != null && this.queryParameters.size() > 0) {
            for (Map.Entry<String, String> header : this.queryParameters.entrySet()) {
                builder.setQueryParameter(header.getKey(), header.getValue());
            }
        }
        if (this.entity != null) {
            builder.entity(this.entity.getSerialized());
        }
        return new ApiRequestImpl(builder.buildAs(method));
    }
}
