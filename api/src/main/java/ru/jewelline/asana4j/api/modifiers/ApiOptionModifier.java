package ru.jewelline.asana4j.api.modifiers;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.entity.SerializableEntity;
import ru.jewelline.request.http.modifiers.ModifiersChain;
import ru.jewelline.request.http.modifiers.RequestModifier;

public abstract class ApiOptionModifier implements RequestModifier {
    protected static final String GET_API_OPTION_PREFIX = "opt_";

    @Override
    public int priority() {
        return RequestModifier.PRIORITY_API_OPTIONS;
    }

    @Override
    public void modify(HttpRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (HttpMethod.GET == httpMethod) {
            appendToQueryParameters(requestBuilder);
        } else if (HttpMethod.DELETE != httpMethod) {
            tryAppendToJsonOptions(requestBuilder);
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }

    protected abstract void appendToQueryParameters(HttpRequestBuilder requestBuilder);

    private void tryAppendToJsonOptions(HttpRequestBuilder requestBuilder) {
        SerializableEntity entity = requestBuilder.getEntity();

    }
}