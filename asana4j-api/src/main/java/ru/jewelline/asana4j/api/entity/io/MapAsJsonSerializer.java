package ru.jewelline.asana4j.api.entity.io;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

public class MapAsJsonSerializer implements EntitySerializer {
    @Override
    public InputStream serialize(Object entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof Map) {
            return new ByteArrayInputStream(new JSONObject(entity).toString().getBytes());
        }
        throw new ApiException(ApiException.API_ENTITY_SERIALIZATION_FAIL, "MapAsJsonSerializer was called on unknown entity type ("
                + entity.getClass().getName() + "), " + entity.toString());
    }
}
