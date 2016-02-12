package ru.jewelline.asana.common;

public interface EntityContext {

    <T, R extends T> T getEntity(Class<R> entityClass);

    <T> EntityBasedOutputStream<T> getReader(Class<? extends T> entityClass);
}
