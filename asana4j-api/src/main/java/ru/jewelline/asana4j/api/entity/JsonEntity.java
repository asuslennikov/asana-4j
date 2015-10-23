package ru.jewelline.asana4j.api.entity;

import org.json.JSONObject;


public interface JsonEntity extends SerializableEntity {

    /**
     * Converts entity into JSON format. This method MUST always return the same instance for the entity.
     * @return a json representation of entity.
     */
    JSONObject asJson();
}
