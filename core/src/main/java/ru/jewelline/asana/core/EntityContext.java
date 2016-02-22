package ru.jewelline.asana.core;

public interface EntityContext {

    <T, E> EntityResponseReader<T, E> getReader(Class<T> entityClass, Class<E> errorClass);
}
