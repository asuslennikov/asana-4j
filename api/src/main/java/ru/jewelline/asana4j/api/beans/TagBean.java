package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.entities.ProjectColor;
import ru.jewelline.asana4j.api.entities.Tag;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.asana4j.api.entities.Workspace;

import java.util.List;

public class TagBean extends BaseBean implements Tag {
    private long id;
    private String name;
    private String createdAt;
    private List<User> followers;
    private ProjectColor color;
    private String notes;
    private Workspace workspace;

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
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
}
