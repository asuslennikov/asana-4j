package ru.jewelline.asana4j.core.impl.clients.modifiers;

import org.json.JSONObject;
import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.entity.JsonEntity;
import ru.jewelline.request.api.entity.SerializableEntity;
import ru.jewelline.request.api.modifiers.ModifiersChain;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

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
