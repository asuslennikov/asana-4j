package ru.jewelline.request.http.entity;

import java.io.InputStream;

public interface SerializableEntity {

    InputStream getSerialized();
}
