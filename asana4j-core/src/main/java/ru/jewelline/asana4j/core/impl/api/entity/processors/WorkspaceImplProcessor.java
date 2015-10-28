package ru.jewelline.asana4j.core.impl.api.entity.processors;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityFieldWriter;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;

public enum WorkspaceImplProcessor implements ApiEntityFieldWriter<Workspace, WorkspaceImpl>, ApiEntityFieldReader<Workspace, WorkspaceImpl> {
    ID ("id") {
        @Override
        protected void convertInternal(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }

        @Override
        protected void convertInternal(WorkspaceImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getId());
        }
    },
    NAME ("name") {
        @Override
        protected void convertInternal(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }

        @Override
        protected void convertInternal(WorkspaceImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getName());
        }
    },
    ORGANISATION ("is_organization") {
        @Override
        protected void convertInternal(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setOrganisation(source.getBoolean(getFieldName()));
        }

        @Override
        protected void convertInternal(WorkspaceImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.isOrganisation());
        }
    },
    ;
    private String fieldName;

    WorkspaceImplProcessor(String fieldName) {
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
                    "Unable parse field '" + this.getFieldName() + "' from json response " + source);
        }
    }

    protected abstract void convertInternal(JSONObject source, WorkspaceImpl target) throws JSONException;

    @Override
    public void convert(WorkspaceImpl source, JSONObject target) {
        try {
            convertInternal(source, target);
        } catch (JSONException ex){
            throw new ApiException(ApiException.API_ENTITY_SERIALIZATION_FAIL,
                    "Unable to serialize field '" + this.getFieldName() + "', source " + source);
        }
    }

    protected abstract void convertInternal(WorkspaceImpl source, JSONObject target) throws JSONException;
}
