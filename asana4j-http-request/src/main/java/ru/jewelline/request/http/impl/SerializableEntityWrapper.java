package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.entity.EntitySerializer;
import ru.jewelline.request.http.entity.SerializableEntity;

import java.io.InputStream;

final class SerializableEntityWrapper<T> implements SerializableEntity {
    private static final Serializer SERIALIZER = new Serializer();

    private final T entity;
    private final EntitySerializer<T> serializer;

    SerializableEntityWrapper(T entity, EntitySerializer<T> serializer) {
        if (serializer == null) {
            throw new IllegalArgumentException("Serializer can not be null");
        }
        this.serializer = serializer;
        this.entity = entity;
    }

    @Override
    public EntitySerializer<SerializableEntity> getSerializer() {
        return SERIALIZER;
    }

    private static final class Serializer implements EntitySerializer<SerializableEntity> {
        @Override
        public InputStream serialize(SerializableEntity wrapper) {
            if (wrapper != null && !(wrapper instanceof SerializableEntityWrapper)) {
                throw new IllegalArgumentException("Incorrect entity type for serialization.");
            }
            return serializeInternal((SerializableEntityWrapper<?>) wrapper);
        }

        private <T> InputStream serializeInternal(SerializableEntityWrapper<T> wrapper) {
            return wrapper != null ? wrapper.serializer.serialize(wrapper.entity) : null;
        }
    }
}
