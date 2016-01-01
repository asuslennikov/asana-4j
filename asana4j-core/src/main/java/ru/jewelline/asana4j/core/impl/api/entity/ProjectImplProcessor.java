package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

import java.util.ArrayList;
import java.util.List;

public enum  ProjectImplProcessor implements JsonFieldReader<ProjectImpl> {
    ID("id") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    OWNER("owner") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setOwner(target.getContext().getDeserializer(UserImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    CURRENT_STATUS("current_status") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setCurrentStatus(target.getContext().getDeserializer(ProjectStatusImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    DUE_DATE("due_date") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setDueDate(source.getString(getFieldName()));
        }
    },
    ARCHIVED("archived") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setArchived(source.getBoolean(getFieldName()));
        }
    },
    PUBLIC("public") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setPublic(source.getBoolean(getFieldName()));
        }
    },
    MEMBERS("members") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            Object membersAsObj = source.get(getFieldName());
            if (membersAsObj instanceof JSONArray) {
                readMembers(target, (JSONArray) membersAsObj);
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
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setColor(source.getString(getFieldName()));
        }
    },
    NOTES("notes") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setNotes(source.getString(getFieldName()));
        }
    },
    WORKSPACE("workspace") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
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
}
