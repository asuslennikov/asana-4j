package ru.jewelline.request.http;

/**
 * Factory which allows user to create an HTTP requests.
 */
public interface HttpRequestFactory {
    /**
     * @return A new builder for HTTP requests.
     */
    HttpRequestBuilder httpRequest();
}