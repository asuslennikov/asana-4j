package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.ProjectColor;
import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.api.entity.Team;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;

import java.util.ArrayList;
import java.util.List;

enum ProjectImplProcessor implements JsonFieldReader<ProjectImpl>, JsonFieldWriter<ProjectImpl> {
    ID("id") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getId());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getName());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    OWNER("owner") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            User owner = source.getOwner();
            if (owner != null && owner instanceof JsonEntity) {
                target.put(getFieldName(), ((JsonEntity) owner).asJson());
            }
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setOwner(target.getContext().getDeserializer(UserImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    CURRENT_STATUS("current_status") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            ProjectStatus status = source.getCurrentStatus();
            if (status != null && status instanceof JsonEntity) {
                target.put(getFieldName(), ((JsonEntity) status).asJson());
            }
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setCurrentStatus(target.getContext().getDeserializer(ProjectStatusImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    DUE_DATE("due_date") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getDueDate());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setDueDate(source.getString(getFieldName()));
        }
    },
    CREATED_AT("created_at") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getCreatedAt());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setCreatedAt(source.getString(getFieldName()));
        }
    },
    MODIFIED_AT("modified_at") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getModifiedAt());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setModifiedAt(source.getString(getFieldName()));
        }
    },
    ARCHIVED("archived") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.isArchived());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setArchived(source.getBoolean(getFieldName()));
        }
    },
    PUBLIC("public") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.isPublic());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setPublic(source.getBoolean(getFieldName()));
        }
    },
    MEMBERS("members") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            List<User> members = source.getMembers();
            if (members != null && !members.isEmpty()) {
                JSONArray membersAsJson = new JSONArray();
                target.put(getFieldName(), membersAsJson);
                for (User member : members) {
                    if (member != null && member instanceof JsonEntity) {
                        membersAsJson.put(((JsonEntity) member).asJson());
                    }
                }
            }
        }

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
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            List<User> followers = source.getFollowers();
            if (followers != null && !followers.isEmpty()) {
                JSONArray followersAsJson = new JSONArray();
                target.put(getFieldName(), followersAsJson);
                for (User follower : followers) {
                    if (follower != null && follower instanceof JsonEntity) {
                        followersAsJson.put(((JsonEntity) follower).asJson());
                    }
                }
            }
        }

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
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            ProjectColor color = source.getColor() != null ? source.getColor() : ProjectColor.NONE;
            target.put(getFieldName(), color.getColorCode());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setColor(ProjectColor.getColorByCode(source.getString(getFieldName())));
        }
    },
    NOTES("notes") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getNotes());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setNotes(source.getString(getFieldName()));
        }
    },
    WORKSPACE("workspace") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            Workspace workspace = source.getWorkspace();
            if (workspace != null && workspace instanceof JsonEntity) {
                target.put(getFieldName(), ((JsonEntity) workspace).asJson());
            }
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setWorkspace(target.getContext().getDeserializer(WorkspaceImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    TEAM("team") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            Team team = source.getTeam();
            if (team != null && team instanceof JsonEntity) {
                target.put(getFieldName(), ((JsonEntity) team).asJson());
            }
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setTeam(target.getContext().getDeserializer(TeamImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },;

    private String fieldName;

    ProjectImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
