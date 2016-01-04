package ru.jewelline.asana4j.api.entity;

public interface Task extends HasId, HasName {

    User getAssignee();

    String getAssigneeStatus();

    boolean isCompleted();

    String getNotes();

    void delete();
}
