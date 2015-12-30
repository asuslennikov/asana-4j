package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

import java.util.Arrays;
import java.util.List;

public class UserImpl extends ApiEntityImpl<UserImpl> implements User {

    private long id;
    private String name;
    private String email;
    private String photoUrl;
    private List<Workspace> workspaces;

    public UserImpl() {
        super(UserImpl.class);
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
    public boolean equals(Object candidate) {
        if (this == candidate){
            return true;
        }
        if (candidate == null || getClass() != candidate.getClass()){
            return false;
        }
        return id == ((UserImpl) candidate).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("User [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(", email = ").append(getEmail());
        out.append(']');
        return out.toString();
    }

    @Override
    protected List<JsonFieldReader<UserImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<UserImpl>>asList(UserImplProcessor.values());
    }
}
