package ru.jewelline.asana4j.api.entity;

import java.util.List;

public interface User extends HasId, HasName, JsonEntity<User> {
    String getEmail();

    String getPhotoUrl();

    List<Workspace> getWorkspaces();
}
