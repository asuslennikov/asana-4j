package ru.jewelline.asana4j.http;

/**
 * Entry  point for all HTTP requests
 */
public interface HttpClient {
    /**
     * @return a new builder for HTTP requests
     */
    HttpRequestBuilder newRequest();
}
