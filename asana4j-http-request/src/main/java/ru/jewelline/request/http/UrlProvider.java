package ru.jewelline.request.http;

public interface UrlProvider {

    Builder newBuilder();

    interface Builder {

        Builder path(String path);

        Builder addQueryParameter(String key, String value);

        String build();
    }
}
