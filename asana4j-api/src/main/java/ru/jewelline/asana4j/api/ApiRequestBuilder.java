package ru.jewelline.asana4j.api;

import org.json.JSONObject;
import ru.jewelline.asana4j.http.HttpRequestBuilder;

public interface ApiRequestBuilder<T> extends HttpRequestBuilder {
    String OPTION_PRETTY = "pretty";
    String OPTION_METHOD = "method";
    String OPTION_FIELDS = "fields";
    String OPTION_EXPAND = "expand";

    @Override
    ApiRequestBuilder<T> path(String baseUrl);

    @Override
    ApiRequestBuilder<T> addQueryParameter(String parameterKey, String parameterValue);

    @Override
    ApiRequestBuilder<T> addHeaders(String headerKey, String headerValue);

    @Override
    ApiRequestBuilder<T> entity(byte[] requestBody);

    @Override
    ApiRequestBuilder<T> entity(JSONObject object);

    ApiRequestBuilder<T> addApiOption(String option, Object value);

    @Override
    ApiRequest<T> build();
}
