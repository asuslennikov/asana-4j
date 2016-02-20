package ru.jewelline.request.http;

import ru.jewelline.request.http.config.HttpConfiguration;
import ru.jewelline.request.http.config.SimpleHttpConfiguration;
import ru.jewelline.request.http.impl.HttpRequestFactoryImpl;

public final class HttpRequestFactories {
    private HttpRequestFactories() {
    }

    public static HttpRequestFactory standard() {
        return from(new SimpleHttpConfiguration());
    }

    public static HttpRequestFactory from(HttpConfiguration configuration) {
        return new HttpRequestFactoryImpl(configuration);
    }
}
