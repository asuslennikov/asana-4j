package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.io.EntitySerializer;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;

import java.io.InputStream;

public class CachedJsonEntity<T extends SerializableEntity & JsonEntity> implements SerializableEntity, JsonEntity {
    private final EntitySerializer serializer;
    private final JSONObject json;

    public CachedJsonEntity(T entity) {
        if (entity == null){
            throw new IllegalArgumentException("Entity can not be null");
        }
        this.json = entity.asJson();
        this.serializer = entity.getSerializer();
    }

    @Override
    public JSONObject asJson() {
        return this.json;
    }

    @Override
    public Object fromJson(JSONObject object) {
        throw new UnsupportedOperationException("Cached entity can not be restored back to object");
    }

    @Override
    public InputStream getSerialized() {
        return this.getSerializer().serialize(asJson());
    }

    @Override
    public EntitySerializer getSerializer() {
        return this.serializer;
    }
}
