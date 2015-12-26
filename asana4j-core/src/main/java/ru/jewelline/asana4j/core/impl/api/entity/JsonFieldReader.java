package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.JsonEntity;

public interface JsonFieldReader<T extends JsonEntity<? super T>> {
    String getFieldName();
    void read(JSONObject source, T target);
}
