package ru.jewelline.request.http;

public interface UrlBuilder {

    UrlBuilder path(String path);

    UrlBuilder addQueryParameter(String key, String value);

    String build();

}
