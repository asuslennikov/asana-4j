package ru.jewelline.request.api.impl;

import ru.jewelline.request.api.entity.EntitySerializer;
import ru.jewelline.request.api.entity.SerializableEntity;

import java.io.InputStream;

public final class SerializableEntityImpl<T> implements SerializableEntity {

    private final T entity;
    private final EntitySerializer<T> serializer;

    SerializableEntityImpl(EntitySerializer<T> serializer, T entity) {
        if (serializer == null){
            throw new IllegalArgumentException("Serializer can not be null");
        }
        this.serializer = serializer;
        this.entity = entity;
    }


    @Override
    EntitySerializer<SerializableEntity> getSerializer() {
        return new SerializableEntitySerializer();
    }
    
    private static final class SerializableEntitySerializer<T> implements EntitySerializer{
        private final T entity;
        private final EntitySerializer<T> serializer;

        public SerializableEntitySerializer(T entity, EntitySerializer<T> serializer) {
            this.entity = entity;
            this.serializer = serializer;
        }

        @Override
        public InputStream serialize() {
            return wrapper.serializer.serialize(wrapper.entity);
        }
    }
}
