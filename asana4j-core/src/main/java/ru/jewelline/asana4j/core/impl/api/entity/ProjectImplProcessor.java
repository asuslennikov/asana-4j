package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

import java.util.ArrayList;
import java.util.List;

public enum  ProjectImplProcessor implements JsonFieldReader<ProjectImpl> {
    ID("id") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    OWNER("owner") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setOwner(target.getContext().getDeserializer(UserImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    CURRENT_STATUS("current_status") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setCurrentStatus(target.getContext().getDeserializer(ProjectStatusImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    DUE_DATE("due_date") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setDueDate(source.getString(getFieldName()));
        }
    },
    ARCHIVED("archived") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setArchived(source.getBoolean(getFieldName()));
        }
    },
    PUBLIC("public") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setPublic(source.getBoolean(getFieldName()));
        }
    },
    MEMBERS("members") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            if (!source.isNull(getFieldName())) {
                Object membersAsObj = source.get(getFieldName());
                if (membersAsObj instanceof JSONArray) {
                    readMembers(target, (JSONArray) membersAsObj);
                }
            }
        }

        private void readMembers(ProjectImpl target, JSONArray members) {
            if (members.length() > 0) {
                List<User> converted = new ArrayList<>();
                for (int i = 0; i < members.length(); i++) {
                    converted.add(target.getContext().getDeserializer(UserImpl.class)
                            .deserialize(members.getJSONObject(i)));
                }
                target.setMembers(converted);
            }
        }
    },
    COLOR("color") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setColor(source.getString(getFieldName()));
        }
    },
    NOTES("notes") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setNotes(source.getString(getFieldName()));
        }
    },
    WORKSPACE("workspace") {
        @Override
        protected void readInternal(JSONObject source, ProjectImpl target) throws JSONException {
            target.setWorkspace(target.getContext().getDeserializer(WorkspaceImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    ;

    private String fieldName;

    ProjectImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void read(JSONObject source, ProjectImpl target) {
        try {
            readInternal(source, target);
        } catch (JSONException ex) {
            throw new ApiException(ApiException.INCORRECT_RESPONSE_FIELD_FORMAT,
                    "Unable parse field '" + this.getFieldName() + "' from json response " + source);
        }
    }

    protected abstract void readInternal(JSONObject source, ProjectImpl target) throws JSONException;
}
