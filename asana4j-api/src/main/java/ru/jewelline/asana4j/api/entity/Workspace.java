package ru.jewelline.asana4j.api.entity;

public interface Workspace extends HasId, HasName, JsonEntity<Workspace> {
    boolean isOrganisation();

    void setName(String name);

    User addUser(long userId);

    User addUser(String email);

    User addCurrentUser();

    void removeUser(long userId);

    void removeUser(String email);

    void removeCurrentUser();

    void save();
}
