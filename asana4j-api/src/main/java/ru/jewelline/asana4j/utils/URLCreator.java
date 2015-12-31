package ru.jewelline.asana4j.utils;

public interface URLCreator {

    Builder builder();

    interface Builder {
        Builder path(String path);

        Builder addQueryParameter(String key, String value);

        String build();
    }
}
