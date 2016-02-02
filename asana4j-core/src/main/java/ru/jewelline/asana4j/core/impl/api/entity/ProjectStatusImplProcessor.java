package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;
import ru.jewelline.request.api.entity.JsonEntity;

enum ProjectStatusImplProcessor implements JsonFieldReader<ProjectStatusImpl>, JsonFieldWriter<ProjectStatusImpl> {
    COLOR("color") {
        @Override
        public void write(ProjectStatusImpl source, JSONObject target) throws JSONException {
            ProjectStatus.Color color = source.getColor() != null ? source.getColor() : ProjectStatus.Color.NONE;
            target.put(getFieldName(), color.getColorCode());
        }

        @Override
        public void read(JSONObject source, ProjectStatusImpl target) throws JSONException {
            target.setColor(ProjectStatus.Color.getColorByCode(source.getString(getFieldName())));
        }
    },
    TEXT("text") {
        @Override
        public void write(ProjectStatusImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getText());
        }

        @Override
        public void read(JSONObject source, ProjectStatusImpl target) throws JSONException {
            target.setText(source.getString(getFieldName()));
        }
    },
    AUTHOR("author") {
        @Override
        public void write(ProjectStatusImpl source, JSONObject target) throws JSONException {
            User author = source.getAuthor();
            if (author != null && author instanceof JsonEntity) {
                target.put(getFieldName(), ((JsonEntity) author).asJson());
            }
        }

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
