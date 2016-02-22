package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.entities.Project;
import ru.jewelline.asana4j.api.entities.ProjectColor;
import ru.jewelline.asana4j.api.entities.ProjectStatus;
import ru.jewelline.asana4j.api.entities.Team;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.asana4j.api.entities.Workspace;

import java.util.List;

public class ProjectBean implements Project {

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
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public ProjectStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ProjectStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean isPublic() {
        return pub;
    }

    public void setPublic(boolean isPublic) {
        this.pub = isPublic;
    }

    @Override
    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    @Override
    public ProjectColor getColor() {
        return color;
    }

    public void setColor(ProjectColor color) {
        this.color = color;
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

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
