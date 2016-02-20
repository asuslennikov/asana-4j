package ru.jewelline.asana4j.impl.entity;

import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.Team;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.impl.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.impl.entity.common.JsonFieldWriter;
import ru.jewelline.asana4j.impl.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorkspaceImpl extends ApiEntityImpl<WorkspaceImpl> implements Workspace {

    private long id;
    private String name;
    private boolean organisation;

    public WorkspaceImpl(ApiEntityContext context) {
        super(WorkspaceImpl.class, context);
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

    void setId(long id) {
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
        getContext().apiRequest()
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
        return addUserInternal(email != null ? email : "");
    }

    @Override
    public User addCurrentUser() {
        return addUserInternal("me");
    }

    private User addUserInternal(Object userReference) {
        return getContext().apiRequest()
                .path("workspaces/" + this.getId() + "/addUser")
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
        getContext().apiRequest()
                .path("workspaces/" + this.getId() + "/removeUser")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("user", userReference.toString())
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute();
        // TODO reload after operation?
    }

    @Override
    public PagedList<Project> getProjects() {
        // TODO should we update a workspace of project to this instance?
        return getContext().apiRequest()
                .path("workspaces/" + this.getId() + "/projects")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getContext().getDeserializer(ProjectImpl.class));
    }

    @Override
    public Project createProject(String name) {
        SimpleFieldsUpdater fieldsUpdater = new SimpleFieldsUpdater()
                .setField("workspace", getId())
                .setField("name", name);
        return getContext().apiRequest()
                .path("projects")
                .setEntity(fieldsUpdater.wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getContext().getDeserializer(ProjectImpl.class));
    }

    @Override
    public Task.TaskCreator createTask() {
        return new TaskImplCreator(getContext()).setWorkspace(getId());
    }

    @Override
    public PagedList<Team> getTeams(RequestModifier... requestModifiers) {
        return getContext().apiRequest(requestModifiers)
                .path("organizations/" + getId() + "/teams")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getContext().getDeserializer(TeamImpl.class));
    }

    @Override
    public Tag.TagCreator createTag() {
        return new TagImplCreator(getContext()).setWorkspace(getId());
    }
}
