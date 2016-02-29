package ru.jewelline.request.http;


import ru.jewelline.request.http.entity.SerializableEntity;

import java.util.List;
import java.util.Map;

/**
 * Java object which encapsulates all request parameters and properties (body, headers and etc).
 */
public interface HttpRequest {
    /**
     * @return A target url.
     */
    String getUrl();

    /**
     * @return A map with query parameters, these values will be a part of actual url.
     * It is unmodifiable map.
     */
    Map<String, List<String>> getQueryParameters();

    /**
     * @return A map with header-value pairs, these values will be set as HTTP request headers.
     * It is unmodifiable map.
     */
    Map<String, List<String>> getHeaders();

    /**
     * @return A payload which will be send to server, can be <code>null</code>.
     */
    SerializableEntity getEntity();

    /**
     * @return An HTTP method for the request.
     */
    HttpMethod getMethod();

    /**
     * Executes current request and redirect a response to the responseReceiver. In fact it is equivalent for
     * {@link HttpRequestFactory#execute(HttpRequest, HttpResponseReceiver)}
     *
     * @param responseReceiver destination for HTTP response.
     * @param <T>              exact type for the responseReceiver
     * @return The same responseReceiver, we need it for call chaining.
     */
    <T extends HttpResponseReceiver> T execute(T responseReceiver);
}
