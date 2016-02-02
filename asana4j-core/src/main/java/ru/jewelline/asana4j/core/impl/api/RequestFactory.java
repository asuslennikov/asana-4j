package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpRequestBuilder;

/**
 * An access point for request builders (for {@link HttpRequestBuilder} and {@link ApiRequestBuilder} instances).
 * @see HttpRequestBuilder
 * @see ApiRequestBuilder
 */
public interface RequestFactory {

    /**
     * Returns a new {@link HttpRequestBuilder} instance, which allows to create and execute a plain HTTP request.
     * @return A new {@link HttpRequestBuilder} instance.
     * @see HttpRequestBuilder
     */
    HttpRequestBuilder httpRequest();

    /**
     * Returns a new {@link ApiRequestBuilder} instance, which allows to create and execute an API request.
     * @param requestModifiers Array of modifiers for the request builder.
     * @return A new {@link ApiRequestBuilder} instance.
     * @see RequestModifier
     * @see ApiRequestBuilder
     */
    ApiRequestBuilder apiRequest(RequestModifier... requestModifiers);
}
