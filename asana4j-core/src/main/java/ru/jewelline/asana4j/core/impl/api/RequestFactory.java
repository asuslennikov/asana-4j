package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.modifiers.RequestModifier;

/**
 * An access point for request builders (for {@link HttpRequestBuilder} and {@link HttpRequestBuilder} instances).
 * @see HttpRequestBuilder
 * @see HttpRequestBuilder
 */
public interface RequestFactory {


    /**
     * Returns a new {@link HttpRequestBuilder} instance, which allows to create and execute an API request.
     * @param requestModifiers Array of modifiers for the request builder.
     * @return A new {@link HttpRequestBuilder} instance.
     * @see RequestModifier
     * @see HttpRequestBuilder
     */
    HttpRequestBuilder newRequest(RequestModifier... requestModifiers);
}
