package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.JsonEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StatefulJsonEntity implements JsonEntity {
    private final JSONObject json;

    public StatefulJsonEntity(JsonEntity entity) {
        this.json = entity.asJson();
    }

    @Override
    public JSONObject asJson() {
        return this.json;
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
