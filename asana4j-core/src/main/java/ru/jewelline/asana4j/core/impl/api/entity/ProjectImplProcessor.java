package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;

import java.util.ArrayList;
import java.util.List;

public enum  ProjectImplProcessor implements JsonFieldReader<ProjectImpl>, JsonFieldWriter<ProjectImpl> {
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
    COLOR("color") {
        @Override
        public void write(ProjectImpl source, JSONObject target) throws JSONException {
            Project.Color color = source.getColor() != null ? source.getColor() : Project.Color.NONE;
            target.put(getFieldName(), color.getColorCode());
        }

        @Override
        public void read(JSONObject source, ProjectImpl target) throws JSONException {
            target.setColor(Project.Color.getColorByCode(source.getString(getFieldName())));
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
    ;

    private String fieldName;

    ProjectImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
