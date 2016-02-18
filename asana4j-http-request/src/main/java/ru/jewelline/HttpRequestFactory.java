package ru.jewelline;

import ru.jewelline.request.http.UrlBuilder;
import ru.jewelline.request.http.config.HttpConfiguration;
import ru.jewelline.request.http.modifiers.RequestModifier;

public interface HttpRequestFactory {
    /**
     * @return A current HTTP configuration.
     */
    HttpConfiguration getHttpConfiguration();

    /**
     * @return An URL builder which allows to build URLs with encoded parts and query parameters.
     */
    UrlBuilder urlBuilder();

    HttpRequestBuilder newRequest(RequestModifier... requestModifiers);

    void execute(HttpRequest request, HttpResponseReceiver responseRecevier);
}
