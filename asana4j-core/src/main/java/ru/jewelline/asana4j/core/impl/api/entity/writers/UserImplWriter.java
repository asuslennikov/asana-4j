package ru.jewelline.asana4j.core.impl.api.entity.writers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityFieldWriter;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;

import java.util.ArrayList;
import java.util.List;

public enum UserImplWriter implements ApiEntityFieldWriter<User, UserImpl> {
    ID("id") {
        @Override
        protected void convertInternal(JSONObject source, UserImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    EMAIL("email") {
        @Override
        protected void convertInternal(JSONObject source, UserImpl target) throws JSONException {
            target.setEmail(source.getString(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        protected void convertInternal(JSONObject source, UserImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    PHOTO("photo") {
        @Override
        protected void convertInternal(JSONObject source, UserImpl target) throws JSONException {
            if (!source.isNull(getFieldName())) {
                target.setPhotoUrl(source.getString(getFieldName()));
            }
        }
    },
    WORKSPACES("workspaces") {
        @Override
        protected void convertInternal(JSONObject source, UserImpl target) throws JSONException {
            if (!source.isNull(getFieldName())) {
                Object workspacesAsObj = source.get(getFieldName());
                if (workspacesAsObj instanceof JSONArray) {
                    JSONArray workspaces = (JSONArray) workspacesAsObj;
                    List<Workspace> converted = new ArrayList<>();
                    for (int i = 0; i < workspaces.length(); i++) {
                        converted.add(new WorkspaceImpl().fromJson(workspaces.getJSONObject(i)));
                    }
                    target.setWorkspaces(converted);
                }
            }
        }
    },
    ;

    private String fieldName;

    UserImplWriter(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void convert(JSONObject source, UserImpl target) {
        try {
            convertInternal(source, target);
        } catch (JSONException ex) {
            throw new ApiException(ApiException.INCORRECT_RESPONSE_FIELD_FORMAT,
                    "Unable parse field '" + this.getFieldName() + "' from json response " + source.toString());
        }
    }

    protected abstract void convertInternal(JSONObject source, UserImpl target) throws JSONException;
}
