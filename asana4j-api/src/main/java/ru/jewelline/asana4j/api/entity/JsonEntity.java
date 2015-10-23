package ru.jewelline.asana4j.api.entity;

import org.json.JSONObject;

/**
 * This interface provides an ability not only serialize entity into stream, but also convert it into JSON format.
 */
public interface JsonEntity extends SerializableEntity {

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
}
