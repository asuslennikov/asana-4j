package ru.jewelline.asana4j.core.impl.api.entity.io;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.io.EntitySerializer;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;

import java.io.InputStream;
import java.util.Map;

public class CachedJsonEntity implements JsonEntity {
    private final EntitySerializer<JsonEntity> serializer;
    private final JSONObject json;

    public CachedJsonEntity(JsonEntity entity) {
        if (entity == null){
            throw new IllegalArgumentException("Entity can not be null.");
        }
        this.json = entity.asJson();
        this.serializer = entity.getSerializer();
    }

    public CachedJsonEntity(Map<String, ?> source){
        if (source == null){
            throw new IllegalArgumentException("Source map can not be null.");
        }
        this.json = new JSONObject(source);
        this.serializer = JsonEntitySerializer.INSTANCE;
    }

    @Override
    public JSONObject asJson() {
        return this.json;
    }

    @Override
    public InputStream getSerialized() {
        return this.getSerializer().serialize(this);
    }

    @Override
    public EntitySerializer<JsonEntity> getSerializer() {
        return this.serializer;
    }
}
