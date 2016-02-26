package ru.jewelline.asana4j.core.impl.api.clients.modifiers;

import ru.jewelline.asana4j.api.HttpRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.ModifiersChain;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.entity.SerializableEntity;
import ru.jewelline.request.http.modifiers.RequestModifier;

import java.util.EnumSet;

public class JsonContentTypeModifier implements RequestModifier {
    @Override
    public int priority() {
        return RequestModifier.PRIORITY_BASE_MODIFIER;
    }

    @Override
    public void modify(HttpRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (EnumSet.of(HttpMethod.POST, HttpMethod.PUT).contains(httpMethod)) {
            SerializableEntity entity = requestBuilder.getEntity();
            if (entity instanceof JsonEntity) {
                requestBuilder.setHeader("Content-Type", "application/json");
            }
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
