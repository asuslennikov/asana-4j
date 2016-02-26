package ru.jewelline.request.http;

public interface UrlBuilder {

    UrlBuilder path(String path);

    UrlBuilder setQueryParameter(String key, String... values);

    String build();

}
