package ru.jewelline.asana4j.core.impl.api.entity.workspace;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.JsonEntity;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.JsonFieldWriter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserManagementEntity extends ApiEntityImpl<UserManagementEntity> implements JsonEntity<UserManagementEntity> {

    private enum UserManagementEntityProcessor implements JsonFieldWriter<UserManagementEntity> {
        WORKSPACE {
            @Override
            protected void writeInternal(UserManagementEntity source, JSONObject target) throws JSONException {
                target.put("workspace", source.workspaceId);
            }
        },

        USER {
            @Override
            protected void writeInternal(UserManagementEntity source, JSONObject target) throws JSONException {
                target.put("user", source.userReference);
            }
        },;

        @Override
        public void write(UserManagementEntity source, JSONObject target) {
            try {
                writeInternal(source, target);
            } catch (JSONException ex) {
                throw new ApiException(ApiException.API_ENTITY_SERIALIZATION_FAIL,
                        "Unable to serialize field '" + this.name() + "', source " + source);
            }
        }

        protected abstract void writeInternal(UserManagementEntity source, JSONObject target) throws JSONException;
    }

    private long workspaceId;
    private Object userReference;

    public UserManagementEntity(long workspaceId, Object userReference) {
        super(UserManagementEntity.class);
        this.workspaceId = workspaceId;
        this.userReference = userReference;
    }

    @Override
    protected List<JsonFieldReader<UserManagementEntity>> getFieldReaders() {
        return Collections.emptyList();
    }

    @Override
    protected List<JsonFieldWriter<UserManagementEntity>> getFieldWriters() {
        return Arrays.<JsonFieldWriter<UserManagementEntity>>asList(UserManagementEntityProcessor.values());
    }
}
