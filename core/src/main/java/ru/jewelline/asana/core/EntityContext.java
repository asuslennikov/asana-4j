package ru.jewelline.asana.core;

public interface EntityContext {

    <T> EntityResponseReader<T> getReader(Class<T> entityClass);

    <T> EntityResponseReader<T> getReader(T entity);

    <T, E> EntityWithErrorResponseReader<T, E> getReader(Class<T> entityClass, Class<E> errorClass);

    <T, E> EntityWithErrorResponseReader<T, E> getReader(T entity, E error);
}
