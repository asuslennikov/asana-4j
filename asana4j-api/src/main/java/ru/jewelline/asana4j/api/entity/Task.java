package ru.jewelline.asana4j.api.entity;

import java.util.List;

public interface Task extends HasId, HasName {

    User getAssignee();

    String getAssigneeStatus();

    String getCreatedAt();

    boolean isCompleted();

    String getCompletedAt();

    String getDueOn();

    String getDueAt();

    List<User> getFollowers();

    boolean isHearted();

    List<User> getHeartsAuthors();

    String getModifiedAt();

    int getNumberOfHearts();

    List<Project> getProjects();

    Task getParent();

    String getNotes();

    Workspace getWorkspace();

    void delete();
}
