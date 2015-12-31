package ru.jewelline.asana4j.api.entity;

import java.util.List;

public interface Project extends HasId, HasName {
    User getOwner();

    ProjectStatus getCurrentStatus();

    /**
     * The day on which this project is due. This takes a date with format YYYY-MM-DD.
     */
    String getDueDate();

    boolean isArchived();

    boolean isPublic();

    List<User> getMembers();

    String getColor();

    String getNotes();

    Workspace getWorkspace();
}
