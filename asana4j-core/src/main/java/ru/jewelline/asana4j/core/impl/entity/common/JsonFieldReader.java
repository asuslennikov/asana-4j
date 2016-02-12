package ru.jewelline.asana4j.core.impl.entity.common;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.request.api.entity.JsonEntity;

public interface JsonFieldReader<T extends JsonEntity> {

    String getFieldName();

    void read(JSONObject source, T target) throws JSONException;
}
