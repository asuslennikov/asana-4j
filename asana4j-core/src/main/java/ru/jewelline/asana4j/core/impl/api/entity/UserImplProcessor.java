package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

public enum UserImplProcessor implements JsonFieldReader<UserImpl> {
    ID("id") {
        @Override
        public void read(JSONObject source, UserImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    EMAIL("email") {
        @Override
        public void read(JSONObject source, UserImpl target) throws JSONException {
            target.setEmail(source.getString(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        public void read(JSONObject source, UserImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    PHOTO("photo") {
        @Override
        public void read(JSONObject source, UserImpl target) throws JSONException {
            target.setPhotoUrl(source.getString(getFieldName()));
        }
    },
    WORKSPACES("workspaces") {
        @Override
        public void read(JSONObject source, UserImpl target) throws JSONException {
            Object workspacesAsObj = source.get(getFieldName());
            if (workspacesAsObj instanceof JSONArray) {
                JSONArray workspaces = (JSONArray) workspacesAsObj;
                for (int i = 0; i < workspaces.length(); i++) {
                    target.addWorkspace().fromJson(workspaces.getJSONObject(i));
                }
            }
        }
    },
    ;

    private String fieldName;

    UserImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
