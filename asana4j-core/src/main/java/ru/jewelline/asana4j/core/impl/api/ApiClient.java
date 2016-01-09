package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.http.HttpRequestBuilder;

public interface ApiClient {
    HttpRequestBuilder newRawRequest();

    ApiRequestBuilder newRequest(RequestModifier... requestModifiers);
}
