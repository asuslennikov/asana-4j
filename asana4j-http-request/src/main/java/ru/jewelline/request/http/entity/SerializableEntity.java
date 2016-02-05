package ru.jewelline.request.http.entity;

/**
 * This interface defines contract for entity which can be send as a payload inside {@link ru.jewelline.request.api.ApiRequest}
 */
public interface SerializableEntity {

    /**
     * @return serializer assigned for that entity
     */
    EntitySerializer<SerializableEntity> getSerializer();
}
