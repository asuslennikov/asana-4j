package ru.jewelline.asana4j.http;

import java.io.OutputStream;
import java.util.Map;

public interface HttpResponse<O extends OutputStream> {
    /**
     * @return HTTP server response code
     */
    int code();
    /**
     * @return a map with pairs header-value, these values will be set as http request headers
     */
    Map<String, String> headers();

    /**
     * @return destination stream in which the server response was copied
     */
    O output();
}
