package ru.jewelline.asana4j.api.entity;

import java.util.List;

public interface Workspace extends HasId, HasName {
    boolean isOrganisation();

    void setName(String name);

    User addUser(long userId);

    User addUser(String email);

    User addCurrentUser();

    void removeUser(long userId);

    void removeUser(String email);

    void removeCurrentUser();

    void update();

    List<Project> getProjects();

    Project createProject(String name);

    Task.TaskCreator createTask();
}
