package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

public enum TaskImplProcessor implements JsonFieldReader<TaskImpl> {
    ID("id") {
        @Override
        protected void readInternal(JSONObject source, TaskImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        protected void readInternal(JSONObject source, TaskImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    ASSIGNEE("assignee") {
        @Override
        protected void readInternal(JSONObject source, TaskImpl target) throws JSONException {
            target.setAssignee(target.getContext().getDeserializer(UserImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    ASSIGNEE_STATUS("assignee_status") {
        @Override
        protected void readInternal(JSONObject source, TaskImpl target) throws JSONException {
            target.setAssigneeStatus(source.getString(getFieldName()));
        }
    },
    COMPLETED("completed") {
        @Override
        protected void readInternal(JSONObject source, TaskImpl target) throws JSONException {
            target.setCompleted(source.getBoolean(getFieldName()));
        }
    },
    NOTES("notes") {
        @Override
        protected void readInternal(JSONObject source, TaskImpl target) throws JSONException {
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

    @Override
    public void read(JSONObject source, TaskImpl target) {
        try {
            readInternal(source, target);
        } catch (JSONException ex) {
            throw new ApiException(ApiException.INCORRECT_RESPONSE_FIELD_FORMAT,
                    "Unable parse field '" + this.getFieldName() + "' from json response " + source);
        }
    }

    protected abstract void readInternal(JSONObject source, TaskImpl target) throws JSONException;
}
