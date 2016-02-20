package ru.jewelline.request.http;

import ru.jewelline.request.http.config.HttpConfiguration;
import ru.jewelline.request.http.modifiers.RequestModifier;

/**
 * Factory which allows user to create an HTTP requests.
 */
public interface HttpRequestFactory {
    /**
     * @return A current HTTP configuration.
     */
    HttpConfiguration getHttpConfiguration();

    /**
     * @return An URL builder which allows to build URLs with encoded parts and query parameters.
     */
    UrlBuilder urlBuilder();

    /**
     * @return A new builder for HTTP requests.
     */
    HttpRequestBuilder newRequest(RequestModifier... requestModifiers);

    /**
     * Executes an HTTP request and copy server response into responseReceiver
     *
     * @param request          a request for execution
     * @param responseReceiver a destination for server response
     */
    void execute(HttpRequest request, HttpResponseReceiver responseReceiver);
}
