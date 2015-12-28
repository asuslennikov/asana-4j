package ru.jewelline.asana4j.api.entity;

import java.util.List;

public interface User extends HasId, HasName {
    String getEmail();

    String getPhotoUrl();

    List<Workspace> getWorkspaces();
}
