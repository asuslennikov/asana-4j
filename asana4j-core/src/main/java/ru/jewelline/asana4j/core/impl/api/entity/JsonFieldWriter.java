package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.JsonEntity;

public interface JsonFieldWriter<T extends JsonEntity<? super T>> {
    void write(T source, JSONObject target);
}
