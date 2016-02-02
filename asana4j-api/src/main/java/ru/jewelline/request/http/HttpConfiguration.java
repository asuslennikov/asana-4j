package ru.jewelline.request.http;

/**
 * Provides settings for an HTTP request.
 */
public interface HttpConfiguration {

    /**
     * @return A number of attempts for each HTTP request in case of connection errors.
     */
    int getRetryCount();

    /**
     * @return A time duration (in milliseconds) of awaiting of server response before another attempt.
     */
    int getConnectionTimeout();
}
