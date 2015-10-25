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
     * @return a map with pairs header-value, these values will be set as http request headers.
     * It is unmodifiable map.
     */
    Map<String, String> getHeaders();

    /**
     * @return a representation of HTTP method for which this request instance was created
     */
    HttpMethod getMethod();
    /**
     * @return a payload which will be send to server, can be <code>null</code>
     */
    InputStream getEntity();

    /**
     * Send this HTTP request
     *
     * @return response object (will contain just a HTTP response code)
     */
    HttpResponse<OutputStream> send();

    /**
     * Send this HTTP request and read a server response
     *
     * @param destinationStream output stream in which a server response will be copied
     * @return response object
     */
    <T extends OutputStream> HttpResponse<T> sendAndReadResponse(T destinationStream);
}
