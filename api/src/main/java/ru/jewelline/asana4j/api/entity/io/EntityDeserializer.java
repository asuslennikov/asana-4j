package ru.jewelline.asana4j.api.entity.io;

public interface EntityDeserializer<T> {

    T deserialize(Object serializedEntity);
}
