package ru.jewelline.asana4j.core.impl.api.entity.writers;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityFieldWriter;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;

public enum  WorkspaceImplWriter implements ApiEntityFieldWriter<Workspace, WorkspaceImpl> {
    ID ("id") {
        @Override
        protected void convertInternal(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME ("name") {
        @Override
        protected void convertInternal(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    ;
    private String fieldName;

    WorkspaceImplWriter(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public void convert(JSONObject source, WorkspaceImpl target){
        try {
            convertInternal(source, target);
        } catch (JSONException ex){
            throw new ApiException(ApiException.INCORRECT_RESPONSE_FIELD_FORMAT,
                    "Unable parse field '" + this.getFieldName() + "' from json response " + source.toString());
        }
    }

    protected abstract void convertInternal(JSONObject source, WorkspaceImpl target) throws JSONException;
}
