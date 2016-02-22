package ru.jewelline.asana.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.jewelline.asana.core.AsanaException;
import ru.jewelline.asana.core.EntityResponseReader;
import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.request.http.StreamBasedResponseReceiver;

import java.io.ByteArrayOutputStream;

public class JacksonEntityWithErrorReader<T, E> extends StreamBasedResponseReceiver implements EntityResponseReader<T, E> {

    private static final int DEFAULT_ARRAY_SIZE = 8192;

    private final ObjectMapper objectMapper;
    private final Class<T> entityClass;
    private final Class<E> errorClass;

    public JacksonEntityWithErrorReader(ObjectMapper objectMapper, Class<T> entityClass, Class<E> errorClass) {
        super(new ByteArrayOutputStream(DEFAULT_ARRAY_SIZE), new ByteArrayOutputStream(DEFAULT_ARRAY_SIZE));
        this.objectMapper = objectMapper;
        this.entityClass = entityClass;
        this.errorClass = errorClass;
    }

    @Override
    public boolean hasError() {
        return getResponseCode() >= 400 && getResponseCode() < 600;
    }

    private <T> T readObject(byte[] sourceData, Class<T> sourceClass) {
        try {
            return this.objectMapper.readValue(sourceData, sourceClass);
        } catch (Exception e) {
            throw new AsanaException(AsanaException.DESERIALIZATION_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public E getError() {
        return readObject(((ByteArrayOutputStream) getErrorStream()).toByteArray(), this.errorClass);
    }

    @Override
    public T toEntity() {
        if (hasError()) {
            throw new AsanaException(getResponseCode(), String.valueOf(getError()));
        }
        return readObject(((ByteArrayOutputStream) getResponseStream()).toByteArray(), this.entityClass);
    }

    @Override
    public PagedList<T> toEntityList() {
        return null;
    }
}
