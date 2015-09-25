package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;

import java.util.List;

public abstract class ApiEntityImpl<A> implements ApiEntity<A> {

    protected abstract <T extends ApiEntityImpl<A>> List<ApiEntityFieldWriter<A, T>> getFieldWriters();

    private Class<A> clazz;

    public ApiEntityImpl(Class<A> clazz) {
        this.clazz = clazz;
    }

    public A fromJson(JSONObject object){
        if (object != null) {
            List<ApiEntityFieldWriter<A, ApiEntityImpl<A>>> fieldWriters = getFieldWriters();
            if (fieldWriters != null) {
                for (ApiEntityFieldWriter<A, ApiEntityImpl<A>> writer : fieldWriters) {
                    if (object.has(writer.getFieldName())){
                        writer.convert(object, this);
                    }
                }
                return clazz.cast(this);
            }
        }
        return null;
    }
}
