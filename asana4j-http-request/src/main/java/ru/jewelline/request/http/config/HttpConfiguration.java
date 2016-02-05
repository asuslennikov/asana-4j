package ru.jewelline.request.http.config;

import java.nio.charset.Charset;

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

    /**
     * @return A charset which should be used for urls.
     */
    Charset getUrlCharset();
}
