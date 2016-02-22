package ru.jewelline.asana.json.im.entity.common;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana.json.im.entity.io.JsonEntitySerializer;
import ru.jewelline.asana4j.core.impl.api.ApiEntityInstanceProvider;
import ru.jewelline.request.api.ApiException;
import ru.jewelline.request.api.entity.EntitySerializer;
import ru.jewelline.request.api.entity.JsonEntity;

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

    public ApiEntityContext getContext() {
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

    public T fromJson(JSONObject object) {
        if (object != null) {
            List<JsonFieldReader<T>> fieldReaders = getFieldReaders();
            if (fieldReaders != null && fieldReaders.size() > 1) {
                boolean hasAtLeastOneField = false;
                for (JsonFieldReader<T> reader : fieldReaders) {
                    if (!object.isNull(reader.getFieldName())) {
                        try {
                            reader.read(object, this.clazz.cast(this));
                        } catch (JSONException ex) {
                            throw new ApiException(ApiException.INCORRECT_RESPONSE_FIELD_FORMAT,
                                    "Unable parse field '" + reader.getFieldName() + "' from json response " + object);
                        }
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
            for (JsonFieldWriter<T> writer : fieldWriters) {
                try {
                    writer.write(this.clazz.cast(this), json);
                } catch (JSONException ex) {
                    throw new ApiException(ApiException.API_ENTITY_SERIALIZATION_FAIL,
                            "Unable to serialize field '" + writer.getFieldName() + "', source " + this);
                }
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
