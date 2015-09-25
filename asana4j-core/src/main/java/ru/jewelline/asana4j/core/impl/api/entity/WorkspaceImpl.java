package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.writers.WorkspaceImplWriter;

import java.util.ArrayList;
import java.util.List;

public class WorkspaceImpl extends ApiEntityImpl<Workspace> implements Workspace {

    private long id;
    private String name;

    public WorkspaceImpl() {
        super(Workspace.class);
    }

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
    public boolean equals(Object candidate) {
        if (this == candidate){
            return true;
        }
        if (candidate == null || getClass() != candidate.getClass()){
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
        out.append("]");
        return out.toString();
    }

    @Override
    protected List<ApiEntityFieldWriter<Workspace, WorkspaceImpl>> getFieldWriters() {
        List<ApiEntityFieldWriter<Workspace, WorkspaceImpl>> writers = new ArrayList<>(WorkspaceImplWriter.values().length);
        for (WorkspaceImplWriter field : WorkspaceImplWriter.values()) {
            writers.add(field);
        }
        return writers;
    }
}
