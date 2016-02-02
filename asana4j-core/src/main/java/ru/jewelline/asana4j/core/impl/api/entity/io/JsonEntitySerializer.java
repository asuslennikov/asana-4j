package ru.jewelline.asana4j.core.impl.api.entity.io;

import ru.jewelline.request.api.entity.EntitySerializer;
import ru.jewelline.request.api.entity.JsonEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class JsonEntitySerializer implements EntitySerializer<JsonEntity> {
    public static final JsonEntitySerializer INSTANCE = new JsonEntitySerializer();

    @Override
    public InputStream serialize(JsonEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ByteArrayInputStream(entity.asJson().toString().getBytes());
    }
}
