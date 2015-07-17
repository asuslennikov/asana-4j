package ru.jewelline.asana4j.http;

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
     * @return a payload which will be send to server, can be <code>null</code>
     */
    byte[] getRequestBody();

    /**
     * @return a map with pairs header-value, these values will be set as http request headers
     */
    Map<String, String> getHeaders();

    /**
     * Execute current request and send it via GET HTTP method
     * @return wrapper for server response
     */
    HttpResponse get();
    /**
     * Execute current request and send it via PUT HTTP method
     * @return wrapper for server response
     */
    HttpResponse put();
    /**
     * Execute current request and send it via POST HTTP method
     * @return wrapper for server response
     */
    HttpResponse post();
    /**
     * Execute current request and send it via DELETE HTTP method
     * @return wrapper for server response
     */
    HttpResponse delete();
}
