package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;

public enum WorkspaceImplProcessor implements JsonFieldReader<WorkspaceImpl>, JsonFieldWriter<WorkspaceImpl> {
    ID ("id") {
        @Override
        protected void readInternal(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }

        @Override
        protected void writeInternal(WorkspaceImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getId());
        }
    },
    NAME ("name") {
        @Override
        protected void readInternal(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }

        @Override
        protected void writeInternal(WorkspaceImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getName());
        }
    },
    ORGANISATION ("is_organization") {
        @Override
        protected void readInternal(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setOrganisation(source.getBoolean(getFieldName()));
        }

        @Override
        protected void writeInternal(WorkspaceImpl source, JSONObject target) throws JSONException {
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
    public void read(JSONObject source, WorkspaceImpl target){
        try {
            readInternal(source, target);
        } catch (JSONException ex){
            throw new ApiException(ApiException.INCORRECT_RESPONSE_FIELD_FORMAT,
                    "Unable parse field '" + this.getFieldName() + "' from json response " + source);
        }
    }

    protected abstract void readInternal(JSONObject source, WorkspaceImpl target) throws JSONException;

    @Override
    public void write(WorkspaceImpl source, JSONObject target) {
        try {
            writeInternal(source, target);
        } catch (JSONException ex){
            throw new ApiException(ApiException.API_ENTITY_SERIALIZATION_FAIL,
                    "Unable to serialize field '" + this.getFieldName() + "', source " + source);
        }
    }

    protected abstract void writeInternal(WorkspaceImpl source, JSONObject target) throws JSONException;
}
