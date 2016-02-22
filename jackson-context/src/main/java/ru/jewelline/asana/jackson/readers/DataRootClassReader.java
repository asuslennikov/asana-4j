package ru.jewelline.asana.jackson.readers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.jewelline.asana.core.DataRoot;

import java.io.IOException;

public class DataRootClassReader<T> implements Reader<T> {
    private final Class<T> clazz;

    public DataRootClassReader(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T readValue(ObjectMapper mapper, byte[] source) throws IOException {
        return ((DataRoot<T>) mapper.readValue(source, mapper.getTypeFactory().constructParametricType(DataRoot.class, clazz)))
                .getData();
    }
}
