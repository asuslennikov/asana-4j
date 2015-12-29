package ru.jewelline.asana4j.api.entity.io;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class JsonSerializer implements EntitySerializer {
    public static final JsonSerializer INSTANCE = new JsonSerializer();

    @Override
    public InputStream serialize(Object entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof JSONObject) {
            return new ByteArrayInputStream(entity.toString().getBytes());
        }
        throw new ApiException(ApiException.API_ENTITY_SERIALIZATION_FAIL, "JsonSerializer was called on unknown entity type ("
                + entity.getClass().getName() + "), " + entity.toString());
    }
}
