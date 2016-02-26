package ru.jewelline.asana4j.api.entity.io;

import org.json.JSONObject;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.entity.SerializableEntity;
import ru.jewelline.request.http.modifiers.RequestModifier;

/**
 * This interface indicates that realization can be converted to JSON
 */
public interface JsonEntity extends SerializableEntity {

    /**
     * Converts entity into JSON format. Basically this method will be called more than once per request lifecycle, so
     * it seems reasonable to return the same JSON instance for sequential calls. But final decision is up to implementation,
     * because it highly depends on {@link HttpRequestBuilder} logic (for example the
     * {@link HttpRequestBuilder#setEntity(SerializableEntity)}) can create stateful wrapper)
     * and available {@link RequestModifier}s
     *
     * @return a json representation of entity.
     */
    JSONObject asJson();

    EntitySerializer<JsonEntity> getSerializer();
}
