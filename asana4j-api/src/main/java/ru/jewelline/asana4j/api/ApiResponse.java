package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;

public interface ApiResponse {
    /**
     * @return HTTP server response code
     */
    int code();

    /**
     * Tries to decode the server response and extract an API object
     * @param deserializer a JSON convertor which will be used for a single object in the response
     * @return an instance of API object
     */
    <T, R extends T> T asApiObject(EntityDeserializer<R> deserializer, ResponsePostProcessor... postProcessors);

    /**
     * Tries to decode the server response and extract list of API objects
     * @param deserializer a JSON convertor which will be used for a single object in the response
     * @return a list of API objects
     */
    <T, R extends T> PagedList<T> asApiCollection(EntityDeserializer<R> deserializer, ResponsePostProcessor... postProcessors);
}
