package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.entity.io.EntitySerializer;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;
import ru.jewelline.asana4j.http.HttpMethod;

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
