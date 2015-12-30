package ru.jewelline.asana4j.core.impl.api.entity.common;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.io.EntitySerializer;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.core.impl.api.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.io.JsonEntitySerializer;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public abstract class ApiEntityImpl<T extends JsonEntity> implements JsonEntity, ApiEntityInstanceProvider<T> {
    private final Class<T> clazz;
    private final ApiEntityContext context;

    public ApiEntityImpl(Class<T> clazz, ApiEntityContext context) {
        this.clazz = clazz;
        this.context = context;
    }

    protected ApiEntityContext getContext() {
        return this.context;
    }

    @Override
    public T getInstance() {
        return this.clazz.cast(this);
    }

    protected abstract List<JsonFieldReader<T>> getFieldReaders();

    protected List<JsonFieldWriter<T>> getFieldWriters() {
        return Collections.emptyList();
    }

    @SuppressWarnings("unused")
    public T fromJson(JSONObject object) {
        if (object != null) {
            List<JsonFieldReader<T>> fieldReaders = getFieldReaders();
            if (fieldReaders != null && fieldReaders.size() > 1) {
                boolean hasAtLeastOneField = false;
                for (JsonFieldReader<T> writer : fieldReaders) {
                    if (object.has(writer.getFieldName())) {
                        writer.read(object, this.clazz.cast(this));
                        hasAtLeastOneField = true;
                    }
                }
                if (!hasAtLeastOneField) {
                    throw new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT,
                            "Api response must contain at least one field");
                }
                return this.clazz.cast(this);
            }
        }
        return null;
    }

    @Override
    public JSONObject asJson() {
        List<JsonFieldWriter<T>> fieldWriters = getFieldWriters();
        if (fieldWriters != null && fieldWriters.size() > 0) {
            JSONObject json = new JSONObject();
            for (JsonFieldWriter<T> reader : fieldWriters) {
                reader.write(this.clazz.cast(this), json);
            }
            return json;
        }
        return new JSONObject();
    }

    @Override
    public EntitySerializer<JsonEntity> getSerializer() {
        return JsonEntitySerializer.INSTANCE;
    }

    @Override
    public InputStream getSerialized() {
        return getSerializer().serialize(this);
    }
}
