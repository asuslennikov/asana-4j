package ru.jewelline.asana4j.api.clients.modifiers;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;
import ru.jewelline.asana4j.http.HttpMethod;

public abstract class ApiOptionModifier implements RequestModifier {
    protected static final String GET_API_OPTION_PREFIX = "opt_";

    @Override
    public int priority() {
        return RequestModifier.PRIORITY_API_OPTIONS;
    }

    @Override
    public void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (HttpMethod.GET == httpMethod) {
            appendToQueryParameters(requestBuilder);
        } else if (HttpMethod.DELETE != httpMethod) {
            tryAppendToJsonOptions(requestBuilder);
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }

    protected abstract void appendToQueryParameters(ApiRequestBuilder requestBuilder);

    private void tryAppendToJsonOptions(ApiRequestBuilder requestBuilder) {
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