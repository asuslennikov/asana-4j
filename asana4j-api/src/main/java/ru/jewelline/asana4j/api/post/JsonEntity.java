package ru.jewelline.asana4j.api.post;

import org.json.JSONObject;

public interface JsonEntity extends SerializableEntity {

    JSONObject toJson();
}
