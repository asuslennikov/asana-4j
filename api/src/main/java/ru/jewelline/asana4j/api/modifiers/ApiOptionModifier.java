package ru.jewelline.asana4j.api.modifiers;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entities.JsonEntity;
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
        if (entity != null && entity instanceof JsonEntity) {
            JSONObject json = ((JsonEntity) entity).asJson();
            if (json != null) {
                JSONObject options;
                if (!json.isNull("options")) {
                    options = json.getJSONObject("options");
                } else {
                    options = new JSONObject();
                    json.put("options", options);
                }
                appendToJsonOptions(options);
            }
        }
    }

    protected abstract void appendToJsonOptions(JSONObject options);
}