package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

public enum TaskImplProcessor implements JsonFieldReader<TaskImpl> {
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
            target.setAssigneeStatus(source.getString(getFieldName()));
        }
    },
    COMPLETED("completed") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setCompleted(source.getBoolean(getFieldName()));
        }
    },
    NOTES("notes") {
        @Override
        public void read(JSONObject source, TaskImpl target) throws JSONException {
            target.setNotes(source.getString(getFieldName()));
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
