package ru.jewelline.request.http;

import ru.jewelline.request.http.modifiers.RequestModifier;

/**
 * Factory which allows user to create an HTTP requests.
 */
public interface HttpRequestFactory {

    /**
     * @return An URL builder which allows to build URLs with encoded parts and query parameters.
     */
    UrlBuilder urlBuilder();

    /**
     * @return A new builder for HTTP requests.
     */
    HttpRequestBuilder newRequest(RequestModifier... requestModifiers);
}
