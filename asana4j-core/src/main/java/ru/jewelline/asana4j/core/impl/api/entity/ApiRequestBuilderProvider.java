package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;

public interface ApiRequestBuilderProvider {
    ApiRequestBuilder newRequest(RequestModifier... requestModifiers);
}
