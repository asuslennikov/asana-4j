package ru.jewelline.asana4j.utils;

public interface PropertiesStore {

    String getString(String key);

    void setString(String key, String value);
}
