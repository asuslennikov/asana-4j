package ru.jewelline.request.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Java object which encapsulates all request parameters and properties (body, headers and etc).
 */
public interface HttpRequest {
    /**
     * @return A full target url.
     */
    String getUrl();

    /**
     * @return A map with header-value pairs, these values will be set as HTTP request headers.
     * It is unmodifiable map.
     */
    Map<String, String> getHeaders();

    /**
     * @return A representation of HTTP method for which this request instance was created.
     */
    HttpMethod getMethod();

    /**
     * @return A payload which will be send to server, can be <code>null</code>.
     */
    InputStream getEntity();

    /**
     * Sends this HTTP request and doesn't read a server response (so no headers, no payload, even a code status
     * is unknown).
     */
    void send();

    /**
     * Send this HTTP request and read a server response into {@link java.io.ByteArrayOutputStream} instance.
     *
     * @return A response object.
     */
    HttpResponse<?> execute();

    /**
     * Send this HTTP request and read a server response.
     *
     * @param destinationStream output stream in which a server response will be copied
     * @return A response object.
     */
    <T extends OutputStream> HttpResponse<T> execute(T destinationStream);
}
