package ru.jewelline.asana4j.core.impl.api.clients.modifiers;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.ModifiersChain;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;
import ru.jewelline.asana4j.http.HttpMethod;

public class DataRootRequestModifier implements RequestModifier {
    @Override
    public int priority() {
        return RequestModifier.PRIORITY_VALIDATOR;
    }

    @Override
    public void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        SerializableEntity entity = requestBuilder.getEntity();
        if (entity != null && entity instanceof JsonEntity){
            JSONObject json = ((JsonEntity) entity).asJson();
            if (!json.has("data")){
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
