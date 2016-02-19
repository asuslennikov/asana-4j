package ru.jewelline.asana.common;

public interface EntityContext {

    <T> EntityResponseReceiver<T> getReceiver(Class<T> entityClass);

    <T, E> EntityWithErrorResponseReceiver<T, E> getReceiver(Class<T> entityClass, Class<E> errorClass);
}
