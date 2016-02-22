package ru.jewelline.asana.jackson.readers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ClassReader<T> implements Reader<T> {
    private Class<T> entityClass;

    public ClassReader(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T readValue(ObjectMapper mapper, byte[] source) throws IOException {
        return mapper.readValue(source, this.entityClass);
    }
}
