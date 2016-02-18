package ru.jewelline;


import ru.jewelline.request.http.HttpMethod;

import java.io.InputStream;

public interface HttpRequestBuilder extends HttpRequestPropertyAccessor {

    HttpRequestBuilder setUrl(String url);

    HttpRequestBuilder setQueryParameter(String name, String... value);

    HttpRequestBuilder setHeader(String name, String... value);

    HttpRequestBuilder setEntity(InputStream stream);

    HttpRequestBuilder setEntity(SerializableEntity entity);

    HttpRequest buildAs(HttpMethod httpMethod);
}
