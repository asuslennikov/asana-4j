package ru.jewelline.asana4j.core.impl.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.core.api.entity.ExternalData;
import ru.jewelline.asana4j.core.api.entity.Project;
import ru.jewelline.asana4j.core.api.entity.Tag;
import ru.jewelline.asana4j.core.api.entity.Task;
import ru.jewelline.asana4j.core.api.entity.User;
import ru.jewelline.asana4j.core.impl.entity.common.JsonFieldReader;

import java.util.ArrayList;
import java.util.List;

enum TaskImplProcessor implements JsonFieldReader<TaskImpl> {
    ID("id") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    ASSIGNEE("assignee") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setAssignee(target.getContext().getDeserializer(UserImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    ASSIGNEE_STATUS("assignee_status") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setAssigneeStatus(Task.AssigneeStatus.getStatusByCode(source.getString(getFieldName())));
        }
    },
    CREATED_AT("created_at") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setCreatedAt(source.getString(getFieldName()));
        }
    },
    COMPLETED("completed") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setCompleted(source.getBoolean(getFieldName()));
        }
    },
    COMPLETED_AT("completed_at") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setCompletedAt(source.getString(getFieldName()));
        }
    },
    DUE_ON("due_on") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setDueOn(source.getString(getFieldName()));
        }
    },
    DUE_AT("due_at") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setDueAt(source.getString(getFieldName()));
        }
    },
    EXTERNAL("external"){
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            JSONObject externalData = source.getJSONObject(getFieldName());
            target.setExternalData(new ExternalData(getOptString(externalData, "id"), getOptString(externalData, "data")));
        }

        private String getOptString(JSONObject obj, String key){
            return obj.has(key) ? obj.opt(key).toString() : null;
        }
    },
    FOLLOWERS("followers") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            Object followersAsObj = source.get(getFieldName());
            if (followersAsObj instanceof JSONArray) {
                readFollowers(target, (JSONArray) followersAsObj);
            }
        }

        private void readFollowers(TaskImpl target, JSONArray followers) {
            if (followers != null && followers.length() > 0) {
                List<User> converted = new ArrayList<>();
                for (int i = 0; i < followers.length(); i++) {
                    converted.add(target.getContext().getDeserializer(UserImpl.class)
                            .deserialize(followers.getJSONObject(i)));
                }
                target.setFollowers(converted);
            }
        }
    },
    HEARTED("hearted") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setHearted(source.getBoolean(getFieldName()));
        }
    },
    HEARTS("hearts") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            Object heartsAsObj = source.get(getFieldName());
            if (heartsAsObj instanceof JSONArray) {
                readHearts(target, (JSONArray) heartsAsObj);
            }
        }

        private void readHearts(TaskImpl target, JSONArray hearts) {
            if (hearts != null && hearts.length() > 0) {
                List<User> converted = new ArrayList<>();
                for (int i = 0; i < hearts.length(); i++) {
                    converted.add(target.getContext().getDeserializer(UserImpl.class)
                            .deserialize(hearts.getJSONObject(i)));
                }
                target.setHeartsAuthors(converted);
            }
        }
    },
    MODIFIED_AT("modified_at") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setModifiedAt(source.getString(getFieldName()));
        }
    },
    NOTES("notes") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setNotes(source.getString(getFieldName()));
        }
    },
    NUM_HEARTS("num_hearts") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setNumberOfHearts(source.getInt(getFieldName()));
        }
    },
    PROJECTS("projects") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            Object projectsAsObj = source.get(getFieldName());
            if (projectsAsObj instanceof JSONArray) {
                readProjects(target, (JSONArray) projectsAsObj);
            }
        }

        private void readProjects(TaskImpl target, JSONArray projects) {
            if (projects != null && projects.length() > 0) {
                List<Project> converted = new ArrayList<>();
                for (int i = 0; i < projects.length(); i++) {
                    converted.add(target.getContext().getDeserializer(ProjectImpl.class)
                            .deserialize(projects.getJSONObject(i)));
                }
                target.setProjects(converted);
            }
        }
    },
    PARENT("parent") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setParent(target.getContext().getDeserializer(TaskImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    WORKSPACE("workspace") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setWorkspace(target.getContext().getDeserializer(WorkspaceImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    TAGS("tags") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            Object tagsAsObj = source.get(getFieldName());
            if (tagsAsObj instanceof JSONArray) {
                readTags(target, (JSONArray) tagsAsObj);
            }
        }

        private void readTags(TaskImpl target, JSONArray tags) {
            if (tags != null && tags.length() > 0) {
                List<Tag> converted = new ArrayList<>();
                for (int i = 0; i < tags.length(); i++) {
                    converted.add(target.getContext().getDeserializer(TagImpl.class)
                            .deserialize(tags.getJSONObject(i)));
                }
                target.setTags(converted);
            }
        }
    },
    ;

    private String fieldName;

    TaskImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
