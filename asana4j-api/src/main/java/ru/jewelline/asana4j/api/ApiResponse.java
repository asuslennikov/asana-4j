package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.http.HttpResponse;

import java.util.List;

public interface ApiResponse<T> extends HttpResponse{
    T asApiObject();
    List<T> asApiCollection();
}
