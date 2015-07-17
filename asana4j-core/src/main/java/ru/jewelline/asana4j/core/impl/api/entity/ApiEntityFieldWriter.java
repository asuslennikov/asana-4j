package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;

public interface ApiEntityFieldWriter<A, I extends ApiEntity<A>> {
    String getFieldName();
    boolean containsRequired(JSONObject source);
    void convert(JSONObject source, I target);
}
