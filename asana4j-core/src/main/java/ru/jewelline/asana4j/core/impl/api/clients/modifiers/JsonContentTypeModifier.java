package ru.jewelline.asana4j.core.impl.api.clients.modifiers;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.ModifiersChain;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.EnumSet;

public class JsonContentTypeModifier implements RequestModifier {
    @Override
    public int priority() {
        return RequestModifier.PRIORITY_BASE_MODIFIER;
    }

    @Override
    public void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (EnumSet.of(HttpMethod.POST, HttpMethod.PUT).contains(httpMethod)){
            SerializableEntity entity = requestBuilder.getEntity();
            if (entity instanceof JsonEntity){
                requestBuilder.setHeader("Content-Type", "application/json");
            }
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
