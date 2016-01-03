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

    void delete();

    ProjectUpdater startUpdate();

    interface ProjectUpdater{

        ProjectUpdater setName(String name);

        ProjectUpdater setOwner(User user);

        ProjectUpdater setStatus(ProjectStatus.Color color, String text, User author);

        ProjectUpdater setDueDate(String date);

        ProjectUpdater setColor(String color);

        ProjectUpdater setNotes(String notes);

        ProjectUpdater setArchived(boolean isArchived);

        ProjectUpdater setPublic(boolean isPublic);

        Project abandon();

        Project update();
    }
}
