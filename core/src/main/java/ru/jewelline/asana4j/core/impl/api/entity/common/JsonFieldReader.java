package ru.jewelline.asana4j.core.impl.api.entity.common;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonFieldReader<T> {

    String getFieldName();

    void read(JSONObject source, T target) throws JSONException;
}
