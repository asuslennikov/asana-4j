package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.JsonEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StatefulJsonEntity<T> implements JsonEntity<T> {
    private final JSONObject json;

    public StatefulJsonEntity(JsonEntity<T> entity) {
        this.json = entity.asJson();
    }

    @Override
    public JSONObject asJson() {
        return this.json;
    }

    @Override
    public T fromJson(JSONObject object) {
        throw new UnsupportedOperationException("Stateful json entity can not be converted back to API instance.");
    }

    @Override
    public InputStream getSerialized() {
        JSONObject json = asJson();
        if (json != null) {
            return new ByteArrayInputStream(json.toString().getBytes());
        }
        return null;
    }
}
