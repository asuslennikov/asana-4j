package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

enum ProjectStatusImplProcessor implements JsonFieldReader<ProjectStatusImpl> {
    COLOR("color") {
        @Override
        public void read(JSONObject source, ProjectStatusImpl target) throws JSONException {
            target.setColor(ProjectStatus.Color.getColorByCode(source.getString(getFieldName())));
        }
    },
    TEXT("text") {
        @Override
        public void read(JSONObject source, ProjectStatusImpl target) throws JSONException {
            target.setText(source.getString(getFieldName()));
        }
    },
    AUTHOR("author") {
        @Override
        public void read(JSONObject source, ProjectStatusImpl target) throws JSONException {
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
}
