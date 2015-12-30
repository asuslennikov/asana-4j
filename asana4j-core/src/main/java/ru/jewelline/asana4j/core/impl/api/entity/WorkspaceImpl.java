package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.ApiRequestBuilderProvider;
import ru.jewelline.asana4j.core.impl.api.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;
import ru.jewelline.asana4j.core.impl.api.entity.io.CachedJsonEntity;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkspaceImpl extends ApiEntityImpl<WorkspaceImpl> implements Workspace {

    private long id;
    private String name;
    private boolean organisation;

    public WorkspaceImpl(ApiRequestBuilderProvider requestBuilderProvider) {
        super(WorkspaceImpl.class, requestBuilderProvider);
    }

    @Override
    protected List<JsonFieldReader<WorkspaceImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<WorkspaceImpl>>asList(WorkspaceImplProcessor.values());
    }

    @Override
    protected List<JsonFieldWriter<WorkspaceImpl>> getFieldWriters() {
        return Collections.<JsonFieldWriter<WorkspaceImpl>>singletonList(WorkspaceImplProcessor.NAME);
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isOrganisation() {
        return organisation;
    }

    public void setOrganisation(boolean organisation) {
        this.organisation = organisation;
    }

    @Override
    public boolean equals(Object candidate) {
        if (this == candidate) {
            return true;
        }
        if (candidate == null || getClass() != candidate.getClass()) {
            return false;
        }
        return id == ((WorkspaceImpl) candidate).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Workspace [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(", organisation = ").append(isOrganisation());
        out.append(']');
        return out.toString();
    }

    @Override
    public void update() {
        this.newRequest()
                .path("workspaces/" + this.getId())
                .setEntity(this)
                .buildAs(HttpMethod.PUT)
                .execute()
                .asApiObject(new ApiEntityDeserializer<>(this));
    }

    @Override
    public User addUser(long userId) {
        return addUserInternal(userId);
    }

    @Override
    public User addUser(String email) {
        return addUserInternal(email);
    }

    @Override
    public User addCurrentUser() {
        return addUserInternal("me");
    }

    private Map<String, Object> getUserManagementMap(Object userReference) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("user", userReference);
        entity.put("workspace", this.getId());
        return entity;
    }

    private User addUserInternal(Object userReference) {
        return this.newRequest()
                .path("workspaces/" + this.getId() + "/addUser")
                .setEntity(new CachedJsonEntity(getUserManagementMap(userReference)))
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(new ApiEntityDeserializer<>(new ApiEntityInstanceProvider<UserImpl>() {
                    @Override
                    public UserImpl getInstance() {
                        return new UserImpl();
                    }
                }));
    }

    @Override
    public void removeUser(long userId) {
        removeUserInternal(userId);
    }

    @Override
    public void removeUser(String email) {
        removeUserInternal(email);
    }

    @Override
    public void removeCurrentUser() {
        removeUserInternal("me");
    }

    private void removeUserInternal(Object userReference) {
        this.newRequest()
                .path("workspaces/" + this.getId() + "/removeUser")
                .setEntity(new CachedJsonEntity(getUserManagementMap(userReference)))
                .buildAs(HttpMethod.POST)
                .execute();
    }
}
