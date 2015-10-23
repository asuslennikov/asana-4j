package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.core.impl.api.ApiEntity;

import java.util.List;

public abstract class ApiEntityImpl<A> implements ApiEntity<A> {

    protected abstract <T extends ApiEntityImpl<A>> List<ApiEntityFieldWriter<A, T>> getFieldWriters();

    private Class<A> clazz;
    private JSONObject jsonRepresentation;

    public ApiEntityImpl(Class<A> clazz) {
        this.clazz = clazz;
    }

    public A fromJson(JSONObject object){
        if (object != null) {
            List<ApiEntityFieldWriter<A, ApiEntityImpl<A>>> fieldWriters = getFieldWriters();
            if (fieldWriters != null) {
                boolean hasAtLeastOneField = false;
                for (ApiEntityFieldWriter<A, ApiEntityImpl<A>> writer : fieldWriters) {
                    if (object.has(writer.getFieldName())){
                        writer.convert(object, this);
                        hasAtLeastOneField = true;
                    }
                }
                if (!hasAtLeastOneField){
                    throw new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT,
                            "Api response must contain at least one field");
                }
                return clazz.cast(this);
            }
        }
        return null;
    }


}
