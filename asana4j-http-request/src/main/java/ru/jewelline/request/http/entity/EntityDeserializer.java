package ru.jewelline.request.http.entity;

import java.io.OutputStream;

public interface EntityDeserializer<T> {

    T deserialize(OutputStream serializedEntity);
}
