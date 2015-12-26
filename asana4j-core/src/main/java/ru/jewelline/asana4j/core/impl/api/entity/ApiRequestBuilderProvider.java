package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.core.impl.api.ApiEntity;

public interface ApiRequestBuilderProvider<AT, T extends ApiEntity<AT>> {
    ApiRequestBuilder<AT> newRequest(ApiEntityInstanceProvider<T> instanceProvider, RequestModifier... requestModifiers);
}
