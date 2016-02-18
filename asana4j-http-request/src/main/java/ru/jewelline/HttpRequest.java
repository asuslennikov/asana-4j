package ru.jewelline;


import ru.jewelline.request.http.HttpMethod;

public interface HttpRequest extends HttpRequestPropertyAccessor {

    HttpMethod getMethod();

    <T extends HttpResponseReceiver> T execute(T responseReceiver);
}
