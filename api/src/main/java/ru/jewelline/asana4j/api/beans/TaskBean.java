package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.entities.ExternalData;
import ru.jewelline.asana4j.api.entities.Project;
import ru.jewelline.asana4j.api.entities.Tag;
import ru.jewelline.asana4j.api.entities.Task;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.asana4j.api.entities.Workspace;

import java.util.List;

public class TaskBean extends BaseBean implements Task {

    private long id;
    private String name;
    private User assignee;
    private AssigneeStatus assigneeStatus;
    private String createdAt;
    private boolean completed;
    private String completedAt;
    private String dueOn;
    private String dueAt;
    private ExternalData externalData;
    private List<User> followers;
    private boolean hearted;
    private List<User> heartsAuthors;
    private String modifiedAt;
    private int numberOfHearts;
    private List<Project> projects;
    private Task parent;
    private String notes;
    private Workspace workspace;
    private List<Tag> tags;

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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @Override
    public AssigneeStatus getAssigneeStatus() {
        return assigneeStatus;
    }

    public void setAssigneeStatus(AssigneeStatus assigneeStatus) {
        this.assigneeStatus = assigneeStatus;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
    public ExternalData getExternalData() {
        return externalData;
    }

    public void setExternalData(ExternalData externalData) {
        this.externalData = externalData;
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
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public Workspace getWorkspace() {
        return workspace;
    }

    @Override
    public boolean isSection() {
        return getName() != null && getName().startsWith(":");
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
