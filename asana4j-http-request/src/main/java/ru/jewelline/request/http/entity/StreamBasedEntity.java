package ru.jewelline.request.http.entity;

import java.io.InputStream;

public final class StreamBasedEntity implements SerializableEntity {
    private final InputStream stream;

    public StreamBasedEntity(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public InputStream getSerialized() {
        return this.stream;
    }
}
