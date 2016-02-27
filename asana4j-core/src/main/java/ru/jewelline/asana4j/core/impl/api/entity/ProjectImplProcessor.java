package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.ProjectColor;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

import java.util.ArrayList;
import java.util.List;

enum ProjectImplProcessor implements JsonFieldReader<ProjectImpl> {
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
    CREATED_AT("created_at") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setCreatedAt(source.getString(getFieldName()));
        }
    },
    MODIFIED_AT("modified_at") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setModifiedAt(source.getString(getFieldName()));
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
    FOLLOWERS("followers") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            Object followerAsObj = source.get(getFieldName());
            if (followerAsObj instanceof JSONArray) {
                readFollowers(target, (JSONArray) followerAsObj);
            }
        }

        private void readFollowers(ProjectImpl target, JSONArray followers) {
            if (followers.length() > 0) {
                List<User> converted = new ArrayList<>();
                for (int i = 0; i < followers.length(); i++) {
                    converted.add(target.getContext().getDeserializer(UserImpl.class)
                            .deserialize(followers.getJSONObject(i)));
                }
                target.setFollowers(converted);
            }
        }
    },
    COLOR("color") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setColor(ProjectColor.getColorByCode(source.getString(getFieldName())));
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
    TEAM("team") {
        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setTeam(target.getContext().getDeserializer(TeamImpl.class)
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
