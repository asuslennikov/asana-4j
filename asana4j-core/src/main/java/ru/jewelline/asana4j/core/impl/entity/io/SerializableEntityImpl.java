package ru.jewelline.asana4j.core.impl.entity.io;

import ru.jewelline.request.api.entity.EntitySerializer;
import ru.jewelline.request.api.entity.SerializableEntity;

import java.io.InputStream;

public final class SerializableEntityImpl<T> implements SerializableEntity {

    private final T entity;
    private final EntitySerializer<T> serializer;

    public SerializableEntityImpl(EntitySerializer<T> serializer, T entity) {
        if (serializer == null){
            throw new IllegalArgumentException("Serializer can not be null");
        }
        this.serializer = serializer;
        this.entity = entity;
    }

    @Override
    public InputStream getSerialized() {
        return this.serializer.serialize(this.entity);
    }

    @Override
    public EntitySerializer<T> getSerializer() {
        return this.serializer;
    }
}
