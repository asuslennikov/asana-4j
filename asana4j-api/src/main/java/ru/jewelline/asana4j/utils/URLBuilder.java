package ru.jewelline.asana4j.utils;

public interface URLBuilder {

    URLBuilder path(String path);
    URLBuilder addQueryParameter(String key, String value);
    String build();
}
