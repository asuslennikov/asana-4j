package ru.jewelline.asana4j.utils;

import org.json.JSONObject;
import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.modifiers.RequestModifier;

/**
 * This interface indicates that realization can be converted to JSON
 */
public interface JsonEntity extends SerializableEntity {

    /**
     * Converts entity into JSON format. Basically this method will be called more than once per request lifecycle, so
     * it seems reasonable to return the same JSON instance for sequential calls. But final decision is up to implementation,
     * because it highly depends on {@link ApiRequestBuilder} logic (for example the
     * {@link ApiRequestBuilder#setEntity(SerializableEntity)}) can create stateful wrapper)
     * and available {@link RequestModifier}s
     *
     * @return a json representation of entity.
     */
    JSONObject asJson();

    @Override
    EntitySerializer<JsonEntity> getSerializer();
}
