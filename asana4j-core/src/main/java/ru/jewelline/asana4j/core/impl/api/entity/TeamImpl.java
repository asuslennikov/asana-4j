package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Team;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.request.http.modifiers.RequestModifier;

import java.util.Arrays;
import java.util.List;

public class TeamImpl extends ApiEntityImpl<TeamImpl> implements Team {

    private long id;
    private String name;

    public TeamImpl(ApiEntityContext context) {
        super(TeamImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<TeamImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<TeamImpl>>asList(TeamImplProcessor.values());
    }

    @Override
    protected List<JsonFieldWriter<TeamImpl>> getFieldWriters() {
        return Arrays.<JsonFieldWriter<TeamImpl>>asList(TeamImplProcessor.values());
    }

    @Override
    public long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id == ((TeamImpl) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Team [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(']');
        return out.toString();
    }

    @Override
    public PagedList<User> getUsers(RequestModifier... requestModifiers) {
        return getContext().newRequest(requestModifiers)
                .setUrl("/teams/" + getId() + "/users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getContext().getDeserializer(UserImpl.class));
    }

    @Override
    public User addUser(long userId) {
        return addUserInternal(userId);
    }

    @Override
    public User addUser(String email) {
        return addUserInternal(email != null ? email : "");
    }

    @Override
    public User addCurrentUser() {
        return addUserInternal("me");
    }

    private User addUserInternal(Object userReference) {
        return getContext().newRequest()
                .setUrl("teams/" + getId() + "/addUser")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("user", userReference.toString())
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getContext().getDeserializer(UserImpl.class));
        // TODO reload after operation?
    }

    @Override
    public void removeUser(long userId) {
        removeUserInternal(userId);
    }

    @Override
    public void removeUser(String email) {
        removeUserInternal(email != null ? email : "");
    }

    @Override
    public void removeCurrentUser() {
        removeUserInternal("me");
    }

    private void removeUserInternal(Object userReference) {
        getContext().newRequest()
                .setUrl("teams/" + getId() + "/removeUser")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("user", userReference.toString())
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute();
        // TODO reload after operation?
    }

    @Override
    public Project createProject(String name) {
        SimpleFieldsUpdater fieldsUpdater = new SimpleFieldsUpdater()
                .setField("name", name);
        return getContext().newRequest()
                .setUrl("teams/" + getId() + "/projects")
                .setEntity(fieldsUpdater.wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getContext().getDeserializer(ProjectImpl.class));
    }
}
