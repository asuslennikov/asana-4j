package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.Attachment;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

public enum AttachmentImplProcessor implements JsonFieldReader<AttachmentImpl> {
    ID("id") {
        @Override
        public void read(JSONObject source, AttachmentImpl target) throws JSONException {
            target.setId(source.getLong(getFieldName()));
        }
    },
    NAME("name") {
        @Override
        public void read(JSONObject source, AttachmentImpl target) throws JSONException {
            target.setName(source.getString(getFieldName()));
        }
    },
    CREATED_AT("created_at") {
        @Override
        public void read(JSONObject source, AttachmentImpl target) throws JSONException {
            target.setCreatedAt(source.getString(getFieldName()));
        }
    },
    DOWNLOAD_URL("download_url") {
        @Override
        public void read(JSONObject source, AttachmentImpl target) throws JSONException {
            target.setDownloadUrl(source.getString(getFieldName()));
        }
    },
    HOST("host") {
        @Override
        public void read(JSONObject source, AttachmentImpl target) throws JSONException {
            target.setHost(Attachment.Host.getHostByType(source.getString(getFieldName())));
        }
    },
    PARENT("parent") {
        @Override
        public void read(JSONObject source, AttachmentImpl target) throws JSONException {
            target.setParent(target.getContext().getDeserializer(TaskImpl.class)
                    .deserialize(source.getJSONObject(getFieldName())));
        }
    },
    VIEW_URL("view_url") {
        @Override
        public void read(JSONObject source, AttachmentImpl target) throws JSONException {
            target.setViewUrl(source.getString(getFieldName()));
        }
    },
    ;

    private String fieldName;

    AttachmentImplProcessor(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
