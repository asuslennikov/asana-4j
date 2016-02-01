package ru.jewelline.asana4j.core.impl.api.entity.io;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.HasId;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class FieldsUpdater {
    private final Map<String, Object> fields;

    public FieldsUpdater() {
        this.fields = new HashMap<>();
    }

    protected void putField(String fieldName, HasId obj) {
        if (obj != null) {
            this.fields.put(fieldName, obj.getId());
        } else {
            this.fields.put(fieldName, JSONObject.NULL);
        }
    }

    protected void putField(String fieldName, Object obj) {
        this.fields.put(fieldName, obj != null ? obj : JSONObject.NULL);
    }

    protected void putField(String fieldName, HasId[] arr){
        if (arr != null){
            Set<Long> ids = new HashSet<>(arr.length);
            for (HasId objWithId : arr) {
                if (objWithId != null) {
                    ids.add(objWithId.getId());
                }
            }
            this.fields.put(fieldName, ids);
        } else {
            this.fields.put(fieldName, Collections.emptyList());
        }
    }

    protected void putField(String fieldName, Object[] arr){
        if (arr != null){
            this.fields.put(fieldName, arr);
        } else {
            this.fields.put(fieldName, Collections.emptyList());
        }
    }

    protected SerializableEntity wrapFieldsAsEntity() {
        return new CachedJsonEntity(this.fields);
    }

    protected Map<String, Object> wrapAsMap(){
        return Collections.unmodifiableMap(this.fields);
    }
}
