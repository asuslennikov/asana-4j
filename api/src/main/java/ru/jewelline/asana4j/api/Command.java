package ru.jewelline.asana4j.api;

import ru.jewelline.request.http.modifiers.RequestModifier;

public interface Command<T> {
    T execute(RequestModifier... requestModifiers);
}
