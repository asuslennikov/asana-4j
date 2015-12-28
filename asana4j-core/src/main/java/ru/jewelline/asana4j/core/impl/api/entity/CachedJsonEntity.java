package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.io.EntitySerializer;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.api.entity.io.JsonSerializer;

import java.io.InputStream;

public class CachedJsonEntity extends SerializableEntityImpl implements JsonEntity {
    private static final EntitySerializer DEFAULT_SERIALIZER = new JsonSerializer();
    private final JSONObject json;

    public CachedJsonEntity(JsonEntity entity) {
        super(DEFAULT_SERIALIZER, entity);
        if (entity == null){
            throw new IllegalArgumentException("Entity can not be null");
        }
        this.json = entity.asJson();
    }

    @Override
    public JSONObject asJson() {
        return this.json;
    }

    @Override
    public InputStream getSerialized() {
        return this.getSerializer().serialize(this.json);
    }
}
