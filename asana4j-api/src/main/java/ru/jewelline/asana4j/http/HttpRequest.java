package ru.jewelline.asana4j.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Java object which encapsulates all request parameters and properties (body, headers and etc).
 */
public interface HttpRequest {
    /**
     * @return a full target url
     */
    String getUrl();

    /**
     * @return a map with pairs header-value, these values will be set as http request headers
     */
    Map<String, String> getHeaders();

    /**
     * @return a payload which will be send to server, can be <code>null</code>
     */
    InputStream getRequestBody();

    /**
     * This method launches the request
     * @return HTTP server response code
     */
    int send();

    /**
     * This method launches the request
     * @param destinationStream output stream in which the server response will be copied
     * @return HTTP server response code
     */
    int sendAndReadResponse(OutputStream destinationStream);
}
