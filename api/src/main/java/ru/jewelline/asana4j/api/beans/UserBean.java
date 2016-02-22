package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.asana4j.api.entities.Workspace;

import java.util.List;
import java.util.Map;

public class UserBean implements User {

    private long id;
    private String name;
    private String email;
    private Map<PhotoSize, String> photoUrl;
    private List<Workspace> workspaces;

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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Map<PhotoSize, String> getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Map<PhotoSize, String> photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }
}
