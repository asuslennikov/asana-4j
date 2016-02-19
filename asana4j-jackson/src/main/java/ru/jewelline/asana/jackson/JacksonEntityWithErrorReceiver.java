package ru.jewelline.asana.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.jewelline.asana.common.EntityWithErrorResponseReceiver;
import ru.jewelline.asana.common.PagedList;
import ru.jewelline.request.http.StreamBasedResponseReceiver;

import java.io.ByteArrayOutputStream;

public class JacksonEntityWithErrorReceiver<T, E> extends StreamBasedResponseReceiver implements EntityWithErrorResponseReceiver<T, E> {

    private static final int DEFAULT_ARRAY_SIZE = 8192;

    private final ObjectMapper objectMapper;
    private final Class<T> entityClass;
    private final Class<E> errorClass;

    public JacksonEntityWithErrorReceiver(ObjectMapper objectMapper, Class<T> entityClass, Class<E> errorClass) {
        super(new ByteArrayOutputStream(DEFAULT_ARRAY_SIZE), new ByteArrayOutputStream(DEFAULT_ARRAY_SIZE));
        this.objectMapper = objectMapper;
        this.entityClass = entityClass;
        this.errorClass = errorClass;
    }

    @Override
    public boolean hasError() {
        return getResponseCode() >= 400 && getResponseCode() < 600;
    }

    @Override
    public E getError() {
        try {
            return this.objectMapper.readValue(((ByteArrayOutputStream) getErrorStream()).toByteArray(), this.errorClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T asEntity() {
        try {
            return this.objectMapper.readValue(((ByteArrayOutputStream) getResponseStream()).toByteArray(), this.entityClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PagedList<T> asEntityList() {
        return null;
    }
}
