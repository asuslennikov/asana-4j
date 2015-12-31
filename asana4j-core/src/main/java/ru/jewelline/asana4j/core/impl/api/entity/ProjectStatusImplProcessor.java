package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

public enum ProjectStatusImplProcessor implements JsonFieldReader<ProjectStatusImpl> {
    COLOR("color") {
        @Override
        protected void readInternal(JSONObject source, ProjectStatusImpl target) throws JSONException {
            target.setColor(ProjectStatus.Color.getColorByCode(source.getString(getFieldName())));
        }
    },
    TEXT("text") {
        @Override
        protected void readInternal(JSONObject source, ProjectStatusImpl target) throws JSONException {
            target.setText(source.getString(getFieldName()));
        }
    },
    AUTHOR("author") {
        @Override
        protected void readInternal(JSONObject source, ProjectStatusImpl target) throws JSONException {
            target.setAuthor(target.getContext().getDeserializer(UserImpl.class).deserialize(source.getJSONObject(getFieldName())));
        }
    },
    ;

    private String fieldName;

    ProjectStatusImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void read(JSONObject source, ProjectStatusImpl target) {
        try {
            readInternal(source, target);
        } catch (JSONException ex) {
            throw new ApiException(ApiException.INCORRECT_RESPONSE_FIELD_FORMAT,
                    "Unable parse field '" + this.getFieldName() + "' from json response " + source);
        }
    }

    protected abstract void readInternal(JSONObject source, ProjectStatusImpl target) throws JSONException;
}
