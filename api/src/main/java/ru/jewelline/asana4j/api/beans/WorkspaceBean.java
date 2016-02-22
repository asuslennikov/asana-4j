package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.entities.Workspace;

public class WorkspaceBean implements Workspace {

    private long id;
    private String name;
    private boolean organisation;

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

    //@Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isOrganisation() {
        return organisation;
    }

    public void setOrganisation(boolean organisation) {
        this.organisation = organisation;
    }
}
