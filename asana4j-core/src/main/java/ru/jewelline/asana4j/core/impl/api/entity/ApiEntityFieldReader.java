package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.core.impl.api.ApiEntity;

public interface ApiEntityFieldReader<A, I extends ApiEntity<A>> {
    void convert(I source, JSONObject target);
}
