package ru.jewelline.asana4j.core.impl.api.entity.common;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.request.api.entity.JsonEntity;

public interface JsonFieldWriter<T extends JsonEntity> {

    String getFieldName();

    void write(T source, JSONObject target) throws JSONException;
}
