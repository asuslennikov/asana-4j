package ru.jewelline.request.api;

import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpRequestFactory;

/**
 * Factory which allows to create both: HTTP and API requests.
 *
 * @see HttpRequestFactory
 */
public interface ApiRequestFactory extends HttpRequestFactory {
    /**
     * @return A new API request builder.
     */
    ApiRequestBuilder apiRequest(RequestModifier... requestModifiers);
}
