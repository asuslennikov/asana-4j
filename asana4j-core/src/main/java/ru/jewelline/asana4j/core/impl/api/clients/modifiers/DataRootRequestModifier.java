package ru.jewelline.asana4j.core.impl.api.clients.modifiers;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.HttpRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.ModifiersChain;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.entity.SerializableEntity;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class DataRootRequestModifier implements RequestModifier {
    @Override
    public int priority() {
        return RequestModifier.PRIORITY_VALIDATOR;
    }

    @Override
    public void modify(HttpRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        SerializableEntity entity = requestBuilder.getEntity();
        if (entity != null && entity instanceof JsonEntity) {
            JSONObject json = ((JsonEntity) entity).asJson();
            if (!json.has("data")) {
                JSONObject root = new JSONObject();
                String[] names = JSONObject.getNames(json);
                if (names != null && names.length > 0) {
                    for (String key : names) {
                        root.put(key, json.remove(key));
                    }
                }
                json.put("data", root);
            }
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
