package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.http.HttpRequest;

public interface ApiRequest<T> extends HttpRequest {
    @Override
    ApiResponse<T> get();

    @Override
    ApiResponse<T> put();

    @Override
    ApiResponse<T> post();

    @Override
    ApiResponse<T> delete();
}
