package ru.jewelline.asana4j.api.post;

import java.io.InputStream;
import java.io.OutputStream;

public interface SerializableEntity {

    InputStream serialize();
}
