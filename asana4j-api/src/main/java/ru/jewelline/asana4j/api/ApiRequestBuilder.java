package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.post.SerializableEntity;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Map;

public interface ApiRequestBuilder<T> {
    // TODO move this constants into appropriate RequestModifiers
    String OPTION_PRETTY = "pretty";
    String OPTION_METHOD = "method";
    String OPTION_FIELDS = "fields";
    String OPTION_EXPAND = "expand";
    String OPTION_JSONP = "jsonp";

    ApiRequestBuilder<T> path(String apiSuffix);

    String getPath();

    ApiRequestBuilder<T> setQueryParameter(String parameterKey, String parameterValue);

    Map<String, String> getQueryParameters();

    ApiRequestBuilder<T> setHeader(String headerKey, String headerValue);

    Map<String, String> getHeaders();

    ApiRequestBuilder<T> setEntity(SerializableEntity entity);

    SerializableEntity getEntity();

    ApiRequest<T> buildAs(HttpMethod method);
}
