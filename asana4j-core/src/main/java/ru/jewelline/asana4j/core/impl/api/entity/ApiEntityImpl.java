package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.JsonEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public abstract class ApiEntityImpl<T extends JsonEntity<? super T>> implements ApiEntityInstanceProvider<T> {
    private Class<T> clazz;
    private ApiRequestBuilderProvider<? super T> requestBuilderProvider;

    public ApiEntityImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ApiEntityImpl(Class<T> clazz, ApiRequestBuilderProvider<? super T> requestBuilderProvider) {
        this.clazz = clazz;
        this.requestBuilderProvider = requestBuilderProvider;
    }

    protected ApiRequestBuilder<? super T> newRequest(RequestModifier... requestModifiers) {
        return this.requestBuilderProvider.newRequest(this, requestModifiers);
    }

    @Override
    public T newInstance() {
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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public InputStream getSerialized() {
        JSONObject json = asJson();
        if (json != null) {
            return new ByteArrayInputStream(json.toString().getBytes());
        }
        return null;
    }
}
