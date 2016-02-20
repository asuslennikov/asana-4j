package ru.jewelline.asana.core;

public interface EntityWithErrorResponseReader<T, E> extends EntityResponseReader<T> {

    boolean hasError();

    E getError();
}
