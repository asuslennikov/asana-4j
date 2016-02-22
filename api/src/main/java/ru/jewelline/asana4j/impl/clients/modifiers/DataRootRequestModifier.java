package ru.jewelline.asana4j.impl.clients.modifiers;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.entity.SerializableEntity;
import ru.jewelline.request.http.modifiers.ModifiersChain;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class DataRootRequestModifier implements RequestModifier {
    @Override
    public int priority() {
        return RequestModifier.PRIORITY_VALIDATOR;
    }

    @Override
    public void modify(HttpRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        SerializableEntity entity = requestBuilder.getEntity();

        modifiersChain.next(requestBuilder, httpMethod);
    }
}
