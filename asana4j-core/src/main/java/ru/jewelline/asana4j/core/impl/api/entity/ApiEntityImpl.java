package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.JsonEntity;
import ru.jewelline.asana4j.core.impl.api.ApiEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public abstract class ApiEntityImpl<A> implements ApiEntity<A>, JsonEntity {
    private Class<A> clazz;

    public ApiEntityImpl(Class<A> clazz) {
        this.clazz = clazz;
    }

    protected abstract <T extends ApiEntityImpl<A>> List<ApiEntityFieldWriter<A, T>> getFieldWriters();

    protected <T extends ApiEntity<A>> List<ApiEntityFieldReader<A, T>> getFieldReaders() {
        return Collections.emptyList();
    }

    public A fromJson(JSONObject object) {
        if (object != null) {
            List<ApiEntityFieldWriter<A, ApiEntityImpl<A>>> fieldWriters = getFieldWriters();
            if (fieldWriters != null && fieldWriters.size() > 1) {
                boolean hasAtLeastOneField = false;
                for (ApiEntityFieldWriter<A, ApiEntityImpl<A>> writer : fieldWriters) {
                    if (object.has(writer.getFieldName())) {
                        writer.convert(object, this);
                        hasAtLeastOneField = true;
                    }
                }
                if (!hasAtLeastOneField) {
                    throw new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT,
                            "Api response must contain at least one field");
                }
                return clazz.cast(this);
            }
        }
        return null;
    }

    @Override
    public JSONObject asJson() {
        List<ApiEntityFieldReader<A, ApiEntity<A>>> fieldReaders = getFieldReaders();
        if (fieldReaders != null && fieldReaders.size() > 0) {
            JSONObject json = new JSONObject();
            for (ApiEntityFieldReader<A, ApiEntity<A>> reader : fieldReaders) {
                reader.convert(this, json);
            }
            return json;
        }
        return new JSONObject();
    }

    @Override
    public InputStream getSerialized() {
        JSONObject json = asJson();
        if (json != null) {
            return new ByteArrayInputStream(json.toString().getBytes());
        }
        return null;
    }
}
