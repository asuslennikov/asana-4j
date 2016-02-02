package ru.jewelline.asana4j.core.impl.api.entity.common;

import org.json.JSONObject;
import ru.jewelline.asana4j.core.impl.api.ApiEntityInstanceProvider;
import ru.jewelline.request.api.ApiException;
import ru.jewelline.request.api.entity.EntityDeserializer;

public final class ApiEntityDeserializer<T extends ApiEntityImpl<T>> implements EntityDeserializer<T> {
    private final ApiEntityInstanceProvider<T> instanceProvider;

    public ApiEntityDeserializer(ApiEntityInstanceProvider<T> instanceProvider) {
        this.instanceProvider = instanceProvider;
    }

    @Override
    public T deserialize(Object entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof JSONObject) {
            return deserializeInternal((JSONObject) entity);
        }
        throw new ApiException(ApiException.API_ENTITY_SERIALIZATION_FAIL, "ApiEntityDeserializer was called on unknown entity type ("
                + entity.getClass().getName() + "), " + entity.toString());
    }

    private T deserializeInternal(JSONObject jsonObject) {
        T obj = this.instanceProvider.getInstance();
        obj.fromJson(jsonObject);
        return obj;
    }
}
