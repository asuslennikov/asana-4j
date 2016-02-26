package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.ProjectColor;
import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.Team;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;
import ru.jewelline.request.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

public class ProjectImpl extends ApiEntityImpl<ProjectImpl> implements Project {

    private long id;
    private String name;
    private User owner;
    private ProjectStatus currentStatus;
    private String dueDate;
    private String createdAt;
    private String modifiedAt;
    private boolean archived;
    private boolean pub;
    private List<User> members;
    private List<User> followers;
    private ProjectColor color;
    private String notes;
    private Workspace workspace;
    private Team team;

    private ProjectUpdater updater;

    public ProjectImpl(ApiEntityContext context) {
        super(ProjectImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<ProjectImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<ProjectImpl>>asList(ProjectImplProcessor.values());
    }

    @Override
    protected List<JsonFieldWriter<ProjectImpl>> getFieldWriters() {
        return Arrays.<JsonFieldWriter<ProjectImpl>>asList(ProjectImplProcessor.values());
    }

    @Override
    public long getId() {
        return this.id;
    }

    void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public User getOwner() {
        return this.owner;
    }

    void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public ProjectStatus getCurrentStatus() {
        return this.currentStatus;
    }

    void setCurrentStatus(ProjectStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String getDueDate() {
        return this.dueDate;
    }

    void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getModifiedAt() {
        return modifiedAt;
    }

    void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public boolean isArchived() {
        return this.archived;
    }

    void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean isPublic() {
        return this.pub;
    }

    void setPublic(boolean isPublic) {
        this.pub = isPublic;
    }

    @Override
    public List<User> getMembers() {
        return this.members;
    }

    void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public List<User> getFollowers() {
        return followers;
    }

    void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    @Override
    public ProjectColor getColor() {
        return this.color;
    }

    void setColor(ProjectColor color) {
        this.color = color;
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public Workspace getWorkspace() {
        return this.workspace;
    }

    void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public PagedList<Task> getTasks() {
        return getContext().newRequest()
                .setUrl("projects/" + getId() + "/tasks")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getContext().getDeserializer(TaskImpl.class));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id == ((ProjectImpl) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Project [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(']');
        return out.toString();
    }

    @Override
    public void delete() {
        getContext().newRequest()
                .setUrl("projects/" + getId())
            .buildAs(HttpMethod.DELETE)
            .execute();
    }

    @Override
    public ProjectUpdater startUpdate() {
        if (this.updater != null) {
            throw new IllegalStateException("Another update process is in progress");
        }
        this.updater = new ProjectImplUpdater(this);
        return this.updater;
    }

    public void stopUpdate(){
        this.updater = null;
    }

    @Override
    public Task.TaskCreator createTask() {
        return new TaskImplCreator(getContext()).setProjects(getId());
    }
}
