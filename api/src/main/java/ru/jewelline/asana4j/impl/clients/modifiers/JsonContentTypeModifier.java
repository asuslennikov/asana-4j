package ru.jewelline.asana4j.impl.clients.modifiers;

import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.entity.JsonEntity;
import ru.jewelline.request.api.entity.SerializableEntity;
import ru.jewelline.request.api.modifiers.ModifiersChain;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

import java.util.EnumSet;

public class JsonContentTypeModifier implements RequestModifier {
    @Override
    public int priority() {
        return RequestModifier.PRIORITY_BASE_MODIFIER;
    }

    @Override
    public void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (EnumSet.of(HttpMethod.POST, HttpMethod.PUT).contains(httpMethod)) {
            SerializableEntity entity = requestBuilder.getEntity();
            if (entity instanceof JsonEntity) {
                requestBuilder.setHeader("Content-Type", "application/json");
            }
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
