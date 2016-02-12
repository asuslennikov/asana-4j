package ru.jewelline.request.api.entity;

public interface EntityDeserializer<T> {

    T deserialize(Object serializedEntity);
}
