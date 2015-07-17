package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.core.impl.api.entity.ApiEntity;

public interface ApiEntityInstanceProvider<T extends ApiEntity<?>> {
    T newInstance();
}
