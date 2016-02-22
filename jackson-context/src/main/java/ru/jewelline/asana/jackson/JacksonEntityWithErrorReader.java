package ru.jewelline.asana.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.jewelline.asana.core.AsanaException;
import ru.jewelline.asana.core.EntityResponseReader;
import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.asana.jackson.readers.Reader;
import ru.jewelline.request.http.StreamBasedResponseReceiver;

import java.io.ByteArrayOutputStream;

public class JacksonEntityWithErrorReader<T, E> extends StreamBasedResponseReceiver implements EntityResponseReader<T, E> {

    private static final int DEFAULT_ARRAY_SIZE = 8192;

    private final ObjectMapper objectMapper;
    private final Reader<T> responseReader;
    private final Reader<E> errorReader;

    public JacksonEntityWithErrorReader(ObjectMapper objectMapper, Reader<T> responseReader, Reader<E> errorReader) {
        super(new ByteArrayOutputStream(DEFAULT_ARRAY_SIZE), new ByteArrayOutputStream(DEFAULT_ARRAY_SIZE));
        this.objectMapper = objectMapper;
        this.responseReader = responseReader;
        this.errorReader = errorReader;
    }

    @Override
    public boolean hasError() {
        return getResponseCode() >= 400 && getResponseCode() < 600;
    }

    private <T> T readObject(byte[] sourceData, Reader<T> reader) {
        try {
            return reader.readValue(this.objectMapper, sourceData);
        } catch (Exception e) {
            throw new AsanaException(AsanaException.DESERIALIZATION_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public E getError() {
        return readObject(((ByteArrayOutputStream) getErrorStream()).toByteArray(), this.errorReader);
    }

    @Override
    public T toEntity() {
        if (hasError()) {
            throw new AsanaException(getResponseCode(), String.valueOf(getError()));
        }
        return readObject(((ByteArrayOutputStream) getResponseStream()).toByteArray(), this.responseReader);
    }

    @Override
    public PagedList<T> toEntityList() {
        return null;
    }
}
