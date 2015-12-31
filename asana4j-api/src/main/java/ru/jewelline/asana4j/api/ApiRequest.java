package ru.jewelline.asana4j.api;

import java.io.InputStream;
import java.util.Map;

public interface ApiRequest {
    /**
     * @return a full API url
     */
    String getUrl();

    /**
     * @return a map with pairs header-value, these values will be set as http request headers
     */
    Map<String, String> getHeaders();

    /**
     * @return a stream which contains a request payload, can be <code>null</code>
     */
    InputStream getEntity();

    /**
     * Send this API request and read a server response
     * @return API response
     */
    ApiResponse execute();
}
