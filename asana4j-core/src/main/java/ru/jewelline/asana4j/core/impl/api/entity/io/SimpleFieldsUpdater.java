package ru.jewelline.asana4j.core.impl.api.entity.io;

import ru.jewelline.asana4j.api.entity.HasId;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;

import java.util.Map;

public class SimpleFieldsUpdater extends FieldsUpdater {

    public SimpleFieldsUpdater setField(String fieldName, Object[] arr) {
        super.putField(fieldName, arr);
        return this;
    }

    public SimpleFieldsUpdater setField(String fieldName, HasId[] arr) {
        super.putField(fieldName, arr);
        return this;
    }

    public SimpleFieldsUpdater setField(String fieldName, HasId obj) {
        super.putField(fieldName, obj);
        return this;
    }

    public SimpleFieldsUpdater setField(String fieldName, Object obj) {
        super.putField(fieldName, obj);
        return this;
    }

    @Override
    public SerializableEntity wrapFieldsAsEntity() {
        return super.wrapFieldsAsEntity();
    }

    @Override
    public Map<String, Object> wrapAsMap() {
        return super.wrapAsMap();
    }
}
