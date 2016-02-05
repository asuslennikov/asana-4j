package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.entity.EntitySerializer;
import ru.jewelline.request.http.entity.SerializableEntity;

import java.io.InputStream;

final class SerializedEntityHolder implements SerializableEntity {
    private static final Serializer SERIALIZER = new Serializer();
    private final InputStream serializedEntity;

    SerializedEntityHolder(InputStream serializedEntity) {
        this.serializedEntity = serializedEntity;
    }

    @Override
    public EntitySerializer<SerializableEntity> getSerializer() {
        return SERIALIZER;
    }

    private static final class Serializer implements EntitySerializer<SerializableEntity> {
        @Override
        public InputStream serialize(SerializableEntity wrapper) {
            if (wrapper != null && !(wrapper instanceof SerializedEntityHolder)) {
                throw new IllegalArgumentException("Incorrect entity type for serialization.");
            }
            return wrapper != null
                    ? ((SerializedEntityHolder) wrapper).serializedEntity
                    : null;
        }
    }
}
