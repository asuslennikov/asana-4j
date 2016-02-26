package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

import java.util.ArrayList;
import java.util.List;

enum StoryImplProcessor implements JsonFieldReader<StoryImpl> {
    ID("id") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    CREATED_AT("created_at") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setCreatedAt(source.getString(getFieldName()));
        }
    },
    CREATED_BY("created_by") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setCreatedBy(target.getContext().getDeserializer(UserImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    HEARTED("hearted") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setHearted(source.getBoolean(getFieldName()));
        }
    },
    HEARTS("hearts") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            Object heartsAsObj = source.get(getFieldName());
            if (heartsAsObj instanceof JSONArray) {
                readHearts((JSONArray) heartsAsObj, target);
            }
        }

        private void readHearts(JSONArray hearts, StoryImpl target) {
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
    NUM_HEARTS("num_hearts") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setNumberOfHearts(source.getInt(getFieldName()));
        }
    },
    TEXT("text") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setText(source.getString(getFieldName()));
        }
    },
    HTMLTEXT("html_text") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setHtmlText(source.getString(getFieldName()));
        }
    },
    TARGET("target") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setTarget(target.getContext().getDeserializer(TaskImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    SOURCE("source") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setSource(source.getString(getFieldName()));
        }
    },
    TYPE("type") {
        @Override
        public void read(JSONObject source, StoryImpl target) throws JSONException {
            target.setType(source.getString(getFieldName()));
        }
    },;

    private String fieldName;

    StoryImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }
}
