package ru.jewelline.asana4j.api;

import java.util.List;

public interface ApiResponse<T>{
    /**
     * @return HTTP server response code
     */
    int code();

    /**
     * Tries to decode the server response and extract an API object
     * @return an instance of API object
     */
    T asApiObject();

    /**
     * Tries to decode the server response and extract list of API objects
     * @return a list of API objects
     */
    List<T> asApiCollection();
}
