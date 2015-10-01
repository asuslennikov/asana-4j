package ru.jewelline.asana4j.api.params;

import ru.jewelline.asana4j.api.ApiRequestBuilder;

public interface QueryParameter {
    boolean applyTo(ApiRequestBuilder<?> requestBuilder);
}
