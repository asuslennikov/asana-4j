package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

enum TeamImplProcessor implements JsonFieldReader<TeamImpl> {
    ID("id") {
        @Override
        public void read(JSONObject source, TeamImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        public void read(JSONObject source, TeamImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    ;

    private final String fieldName;

    TeamImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }


    @Override
    public String getFieldName() {
        return this.fieldName;
    }
}
