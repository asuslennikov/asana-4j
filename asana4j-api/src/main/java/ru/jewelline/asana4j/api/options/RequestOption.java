package ru.jewelline.asana4j.api.options;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.http.HttpMethod;

public interface RequestOption {
    <T> ApiRequestBuilder<T> applyTo(ApiRequestBuilder<T> requestBuilder, HttpMethod httpMethod);
}
