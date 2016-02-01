package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;

enum TeamImplProcessor implements JsonFieldReader<TeamImpl>, JsonFieldWriter<TeamImpl> {
    ID("id") {
        @Override
        public void read(JSONObject source, TeamImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }

        @Override
        public void write(TeamImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getId());
        }
    },
    NAME("name") {
        @Override
        public void read(JSONObject source, TeamImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }

        @Override
        public void write(TeamImpl source, JSONObject target) throws JSONException {
            target.put(getFieldName(), source.getName());
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
