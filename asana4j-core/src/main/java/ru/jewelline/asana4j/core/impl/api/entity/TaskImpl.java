package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

public class TaskImpl extends ApiEntityImpl<TaskImpl> implements Task {

    private long id;
    private String name;
    private User assignee;
    private AssigneeStatus assigneeStatus;
    private String createdAt;
    private boolean completed;
    private String completedAt;
    private String dueOn;
    private String dueAt;
    private List<User> followers;
    private boolean hearted;
    private List<User> heartsAuthors;
    private String modifiedAt;
    private int numberOfHearts;
    private List<Project> projects;
    private Task parent;
    private String notes;
    private Workspace workspace;

    public TaskImpl(ApiEntityContext context) {
        super(TaskImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<TaskImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<TaskImpl>>asList(TaskImplProcessor.values());
    }

    @Override
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public User getAssignee() {
        return this.assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @Override
    public AssigneeStatus getAssigneeStatus() {
        return this.assigneeStatus;
    }

    public void setAssigneeStatus(AssigneeStatus assigneeStatus) {
        this.assigneeStatus = assigneeStatus;
    }

    @Override
    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String getDueOn() {
        return dueOn;
    }

    public void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    @Override
    public String getDueAt() {
        return dueAt;
    }

    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    @Override
    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    @Override
    public boolean isHearted() {
        return hearted;
    }

    public void setHearted(boolean hearted) {
        this.hearted = hearted;
    }

    @Override
    public List<User> getHeartsAuthors() {
        return heartsAuthors;
    }

    public void setHeartsAuthors(List<User> heartsAuthors) {
        this.heartsAuthors = heartsAuthors;
    }

    @Override
    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public int getNumberOfHearts() {
        return numberOfHearts;
    }

    public void setNumberOfHearts(int numberOfHearts) {
        this.numberOfHearts = numberOfHearts;
    }

    @Override
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public Task getParent() {
        return parent;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }

    @Override
    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id == ((TaskImpl) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Task [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(", completed = ").append(isCompleted());
        out.append(']');
        return out.toString();
    }

    @Override
    public void delete() {
        getContext().newRequest()
                .path("tasks/" + getId())
                .buildAs(HttpMethod.DELETE)
                .execute();
    }
}
