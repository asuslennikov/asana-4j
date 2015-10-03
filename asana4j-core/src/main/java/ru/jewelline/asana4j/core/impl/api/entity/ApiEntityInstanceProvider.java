package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.core.impl.api.ApiEntity;

public interface ApiEntityInstanceProvider<T extends ApiEntity<?>> {
    T newInstance();
}
