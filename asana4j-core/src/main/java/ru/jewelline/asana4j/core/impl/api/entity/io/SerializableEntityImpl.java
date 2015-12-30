package ru.jewelline.asana4j.core.impl.api.entity.io;

import ru.jewelline.asana4j.api.entity.io.EntitySerializer;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;

import java.io.InputStream;

public class SerializableEntityImpl implements SerializableEntity {

    private final Object entity;
    private final EntitySerializer serializer;

    public SerializableEntityImpl(EntitySerializer serializer, Object entity) {
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
    public EntitySerializer getSerializer() {
        return this.serializer;
    }
}
