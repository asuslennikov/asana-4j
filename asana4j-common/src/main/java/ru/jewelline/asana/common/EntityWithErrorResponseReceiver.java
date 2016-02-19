package ru.jewelline.asana.common;

public interface EntityWithErrorResponseReceiver<T, E> extends EntityResponseReceiver<T> {

    boolean hasError();

    E getError();
}
