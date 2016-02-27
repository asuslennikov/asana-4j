package ru.jewelline.asana4j.core.impl.api.entity.common;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonFieldWriter<T> {

    String getFieldName();

    void write(T source, JSONObject target) throws JSONException;
}
