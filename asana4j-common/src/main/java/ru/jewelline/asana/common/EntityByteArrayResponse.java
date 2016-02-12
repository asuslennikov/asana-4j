package ru.jewelline.asana.common;

import java.io.ByteArrayOutputStream;

public final class EntityByteArrayResponse<T> extends ByteArrayOutputStream {

    private static final int DEFAULT_CAPACITY = 8192;

    public static <T> EntityByteArrayResponse from(EntityByteArrayDecoder<T> decoder) {
        if (decoder == null) {
            throw new IllegalArgumentException("Decoder can not be null");
        }
        return new EntityByteArrayResponse(decoder);
    }

    private final EntityByteArrayDecoder<T> decoder;

    private EntityByteArrayResponse(EntityByteArrayDecoder<T> decoder) {
        super(DEFAULT_CAPACITY);
        this.decoder = decoder;
    }

    public T toEntity() {
        return this.decoder.decode(toByteArray());
    }
}
