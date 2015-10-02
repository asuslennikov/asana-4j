package ru.jewelline.asana4j.core.impl.api;

public interface ApiEntityInstanceProvider<T extends ApiEntity<?>> {
    T newInstance();
}
