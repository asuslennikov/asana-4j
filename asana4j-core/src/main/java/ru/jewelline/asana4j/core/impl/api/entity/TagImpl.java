package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.ProjectColor;
import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

import java.util.Arrays;
import java.util.List;

public class TagImpl extends ApiEntityImpl<TagImpl> implements Tag {
    private long id;
    private String name;
    private String createdAt;
    private List<User> followers;
    private ProjectColor color;
    private String notes;
    private Workspace workspace;

    public TagImpl(ApiEntityContext context) {
        super(TagImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<TagImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<TagImpl>>asList(TagImplProcessor.values());
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
    public String getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
        return color;
    }

    void setColor(ProjectColor color) {
        this.color = color;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public Workspace getWorkspace() {
        return workspace;
    }

    void setWorkspace(Workspace workspace) {
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
        return id == ((TagImpl) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Tag [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(']');
        return out.toString();
    }

}
