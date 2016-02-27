package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

enum WorkspaceImplProcessor implements JsonFieldReader<WorkspaceImpl> {
    ID("id") {
        @Override
        public void read(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        public void read(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    ORGANISATION("is_organization") {
        @Override
        public void read(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setOrganisation(source.getBoolean(getFieldName()));
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
}
