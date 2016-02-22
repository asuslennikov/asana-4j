package ru.jewelline.asana.jackson.readers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public interface Reader<T> {
    T readValue(ObjectMapper mapper, byte[] source) throws IOException;
}
