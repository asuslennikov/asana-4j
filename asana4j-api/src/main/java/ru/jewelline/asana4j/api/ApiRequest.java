package ru.jewelline.asana4j.api;

import org.json.JSONObject;

import java.io.OutputStream;
import java.util.Map;

public interface ApiRequest<T> {
    /**
     * @return a full API url
     */
    String getUrl();

    /**
     * @return a map with pairs header-value, these values will be set as http request headers
     */
    Map<String, String> getHeaders();

    /**
     * @return a JSON object with request payload (entity which will be send to a server)
     */
    JSONObject getRequestEntity();

    /**
     * Send this API request and read a server response
     * @return API response
     */
    ApiResponse<T> execute();
}
