package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.entities.Team;

public class TeamBean implements Team {

    private long id;
    private String name;

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
}
