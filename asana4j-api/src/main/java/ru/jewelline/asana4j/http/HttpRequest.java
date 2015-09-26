package ru.jewelline.asana4j.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Java object which encapsulates all request parameters and properties (body, headers and etc).
 */
public interface HttpRequest<O extends OutputStream> {
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
     * TSend this HTTP request
     * @return HTTP server response code
     */
    HttpResponse<O> send();

    /**
     * Send this HTTP request and read a server response
     * @param destinationStream output stream in which a server response will be copied
     * @return response object
     */
   HttpResponse<O> sendAndReadResponse(O destinationStream);
}
