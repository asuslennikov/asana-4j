package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;

enum WorkspaceImplProcessor implements JsonFieldReader<WorkspaceImpl>, JsonFieldWriter<WorkspaceImpl> {
    ID ("id") {
        @Override
        public void read(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }

        @Override
        public void write(WorkspaceImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getId());
        }
    },
    NAME ("name") {
        @Override
        public void read(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }

        @Override
        public void write(WorkspaceImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getName());
        }
    },
    ORGANISATION ("is_organization") {
        @Override
        public void read(JSONObject source, WorkspaceImpl target) throws JSONException {
            target.setOrganisation(source.getBoolean(getFieldName()));
        }

        @Override
        public void write(WorkspaceImpl source, JSONObject target) throws JSONException {
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
}
