package ru.jewelline.asana4j.impl.clients.modifiers;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.entity.SerializableEntity;
import ru.jewelline.request.http.modifiers.ModifiersChain;
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
            requestBuilder.setHeader("Content-Type", "application/json");
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
