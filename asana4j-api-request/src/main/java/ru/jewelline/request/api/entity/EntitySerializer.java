package ru.jewelline.request.api.entity;

import java.io.InputStream;

public interface EntitySerializer<T> {
    /**
     * This method converts entity into stream, so it can be read and transferred. It is possible that read operation on this
     * stream will be called several times, for example in case of retry operation for request.
     *
     * @param entity Entity for serialization, can be <code>null</code>.
     * @return Input stream with serialized entity. Exact content of this stream is defined by entity itself.
     */
    InputStream serialize(T entity);
}
