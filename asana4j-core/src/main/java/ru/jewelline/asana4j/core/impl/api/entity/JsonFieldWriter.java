package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;

public interface JsonFieldWriter<T extends JsonEntity> {
    void write(T source, JSONObject target);
}
