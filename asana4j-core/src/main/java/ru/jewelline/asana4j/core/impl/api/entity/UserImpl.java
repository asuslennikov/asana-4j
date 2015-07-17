package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.writers.UserImplWriter;

import java.util.ArrayList;
import java.util.List;

public class UserImpl extends ApiEntityImpl<User> implements User {

    private long id;
    private String name;
    private String email;
    private String photoUrl;
    private List<Workspace> workspaces;

    public UserImpl() {
        super(User.class);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPhotoUrl() {
        return this.photoUrl;
    }

    @Override
    public List<Workspace> getWorkspaces() {
        return this.workspaces;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    @Override
    protected List<ApiEntityFieldWriter<User, UserImpl>> getFieldWriters() {
        List<ApiEntityFieldWriter<User, UserImpl>> writers = new ArrayList<>(UserImplWriter.values().length);
        for (UserImplWriter field : UserImplWriter.values()) {
            writers.add(field);
        }
        return writers;
    }
}
