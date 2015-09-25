package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequestBuilder;

import java.io.InputStream;

public interface ApiRequestBuilder<T> extends HttpRequestBuilder {
    String OPTION_PRETTY = "pretty";
    String OPTION_METHOD = "method";
    String OPTION_FIELDS = "fields";
    String OPTION_EXPAND = "expand";
    String OPTION_JSONP = "jsonp";

    @Override
    ApiRequestBuilder<T> path(String apiSuffix);

    @Override
    ApiRequestBuilder<T> setQueryParameter(String parameterKey, String parameterValue);

    @Override
    ApiRequestBuilder<T> setHeader(String headerKey, String headerValue);

    @Override
    ApiRequestBuilder<T> entity(byte[] requestBody);

    @Override
    ApiRequestBuilder<T> entity(InputStream entityStream);

    ApiRequestBuilder<T> addApiOption(String option, Object value);

    @Override
    ApiRequest<T> buildAs(HttpMethod method);
}
