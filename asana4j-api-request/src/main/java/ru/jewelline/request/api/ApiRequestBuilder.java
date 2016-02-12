package ru.jewelline.request.api;

import ru.jewelline.request.api.entity.EntitySerializer;
import ru.jewelline.request.api.entity.SerializableEntity;
import ru.jewelline.request.http.HttpMethod;

import java.util.Map;

public interface ApiRequestBuilder {

    ApiRequestBuilder path(String apiSuffix);

    String getPath();

    ApiRequestBuilder setQueryParameter(String parameterKey, String parameterValue);

    Map<String, String> getQueryParameters();

    ApiRequestBuilder setHeader(String headerKey, String headerValue);

    Map<String, String> getHeaders();

    <T> ApiRequestBuilder setEntity(T entity, EntitySerializer<T> serializer);

    ApiRequestBuilder setEntity(SerializableEntity entity);

    SerializableEntity getEntity();

    ApiRequest buildAs(HttpMethod method);
}
