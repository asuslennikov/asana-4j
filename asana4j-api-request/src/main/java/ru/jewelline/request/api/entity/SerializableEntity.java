package ru.jewelline.request.api.entity;


import ru.jewelline.request.api.ApiRequest;

import java.io.InputStream;

/**
 * This interface defines contract for entity which can be send as a payload inside {@link  ApiRequest}
 */
public interface SerializableEntity {

    /**
     * This method converts entity into stream, so it can be read and transferred. It is possible that read operation on this
     * stream will be called several times, for example in case of retry operation for request.
     * @return input stream with serialized entity. Exact content of this stream is defined by entity itself.
     */
    InputStream getSerialized();

    /**
     * @return serializer assigned for that entity
     */
    EntitySerializer<?> getSerializer();
}
