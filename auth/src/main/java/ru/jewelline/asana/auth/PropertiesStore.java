package ru.jewelline.asana.auth;

public interface PropertiesStore {

    String getString(String key);

    void setString(String key, String value);
}
