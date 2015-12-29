package ru.jewelline.asana4j.api.entity.io;

import org.json.JSONObject;

/**
 * This interface indicates that realization can be converted to and from JSON (serialized and deserialized)
 */
public interface JsonEntity<T> {

    /**
     * Converts entity into JSON format. Basically this method will be called more than once per request lifecycle, so
     * it seems reasonable to return the same JSON instance for sequential calls. But final decision is up to implementation,
     * because it highly depends on {@link ru.jewelline.asana4j.api.ApiRequestBuilder} logic (for example the
     * {@link ru.jewelline.asana4j.api.ApiRequestBuilder#setEntity(SerializableEntity)}) can create stateful wrapper)
     * and available {@link ru.jewelline.asana4j.api.clients.modifiers.RequestModifier}s
     *
     * @return a json representation of entity.
     */
    JSONObject asJson();

    /**
     * Populates the object instance via information from the JSON object
     * @param object source json object
     * @return the same instance with filled parameters
     */
    T fromJson(JSONObject object);
}
