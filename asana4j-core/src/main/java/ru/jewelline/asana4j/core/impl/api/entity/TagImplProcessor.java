package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.ProjectColor;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

import java.util.ArrayList;
import java.util.List;

enum TagImplProcessor implements JsonFieldReader<TagImpl> {
    ID("id") {
        @Override
        public void read(JSONObject source, TagImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        public void read(JSONObject source, TagImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    CREATED_AT("created_at") {
        @Override
        public void read(JSONObject source, TagImpl target) throws JSONException {
            target.setCreatedAt(source.getString(getFieldName()));
        }
    },
    FOLLOWERS("followers") {
        @Override
        public void read(JSONObject source, TagImpl target) throws JSONException {
            Object followersAsObj = source.get(getFieldName());
            if (followersAsObj instanceof JSONArray) {
                readFollowers(target, (JSONArray) followersAsObj);
            }
        }

        private void readFollowers(TagImpl target, JSONArray followers) {
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
    COLOR("color") {
        @Override
        public void read(JSONObject source, TagImpl target) throws JSONException {
            target.setColor(ProjectColor.getColorByCode(source.getString(getFieldName())));
        }
    },
    NOTES("notes") {
        @Override
        public void read(JSONObject source, TagImpl target) throws JSONException {
            target.setNotes(source.getString(getFieldName()));
        }
    },
    WORKSPACE("workspace") {
        @Override
        public void read(JSONObject source, TagImpl target) throws JSONException {
            target.setWorkspace(target.getContext().getDeserializer(WorkspaceImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    ;

    private String fieldName;

    TagImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }


    @Override
    public String getFieldName() {
        return this.fieldName;
    }
}
