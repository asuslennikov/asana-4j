package ru.jewelline.asana4j.http;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * This class provides access to HTTP response information
 *
 * @param <T> exact type of output stream which was used as destination for server response
 */
public interface HttpResponse<T extends OutputStream> {
    /**
     * @return HTTP server response code
     */
    int code();

    /**
     * @return a map with pairs header-value, it is HTTP response headers.
     * It is unmodifiable map.
     */
    Map<String, List<String>> headers();

    /**
     * @return destination stream in which the server response was copied
     */
    T output();
}
