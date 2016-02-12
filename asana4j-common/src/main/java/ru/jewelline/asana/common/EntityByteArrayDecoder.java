package ru.jewelline.asana.common;

public interface EntityByteArrayDecoder<T> {

    T decode(byte[] encodedEntity);
}
