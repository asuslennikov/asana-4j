package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.JsonEntity;

public interface ApiRequestBuilderProvider<T extends JsonEntity<? super T>> {
    ApiRequestBuilder<T> newRequest(ApiEntityInstanceProvider<T> instanceProvider, RequestModifier... requestModifiers);
}
