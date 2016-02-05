package ru.jewelline.request.http;

import ru.jewelline.request.http.config.HttpConfiguration;
import ru.jewelline.request.http.config.SimpleHttpConfiguration;
import ru.jewelline.request.http.impl.HttpRequestFactoryImpl;

public final class HttpRequestFactories {
    private HttpRequestFactories() {
    }

    public static HttpRequestFactory from(UrlProvider urlProvider) {
        return from(new SimpleHttpConfiguration(), urlProvider);
    }

    public static HttpRequestFactory from(HttpConfiguration configuration, UrlProvider urlProvider) {
        return new HttpRequestFactoryImpl(configuration, urlProvider);
    }
}
