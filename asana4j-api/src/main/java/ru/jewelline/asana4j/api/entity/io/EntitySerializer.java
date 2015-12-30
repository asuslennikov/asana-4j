package ru.jewelline.asana4j.api.entity.io;

import java.io.InputStream;

public interface EntitySerializer<T> {
    /**
     * This method converts entity into stream, so it can be read and transferred. It is possible that read operation on this
     * stream will be called several times, for example in case of retry operation for request.
     *
     * @param entity entity for serialization, can be null
     * @return input stream with serialized entity. Exact content of this stream is defined by entity itself.
     */
    InputStream serialize(T entity);
}
