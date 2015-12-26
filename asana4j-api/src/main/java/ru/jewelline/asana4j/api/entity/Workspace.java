package ru.jewelline.asana4j.api.entity;

public interface Workspace extends HasId, HasName, JsonEntity<Workspace> {
    boolean isOrganisation();

    void setName(String name);

    void save();
}
