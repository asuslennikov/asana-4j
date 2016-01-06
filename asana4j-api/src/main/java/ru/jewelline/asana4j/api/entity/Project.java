package ru.jewelline.asana4j.api.entity;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.utils.StringUtils;

import java.util.List;

/**
 * A project represents a prioritized list of tasks in Asana. It exists in a single workspace or organization
 * and is accessible to a subset of users in that workspace or organization, depending on its permissions.
 * <br />
 * Projects in organizations are shared with a single team. You cannot currently change the team of a project
 * via the API. Non-organization workspaces do not have teams and so you should not specify the team of project
 * in a regular workspace.
 * <br />
 * Followers of a project are a subset of the members of that project. Followers of a project will receive all
 * updates including tasks created, added and removed from that project. Members of the project have access to
 * and will receive status updates of the project. Adding followers to a project will add them as members if
 * they are not already, removing followers from a project will not affect membership.
 *
 * @api.link <a href="https://asana.com/developers/api-reference/projects">Projects API page</a>
 * @see ru.jewelline.asana4j.api.entity.HasId
 * @see ru.jewelline.asana4j.api.entity.HasName
 */
public interface Project extends HasId, HasName {

    /**
     * @return Name of the project. This is generally a short sentence fragment that fits on a line in the UI
     * for maximum readability. However, it can be longer.
     * @api.field <code>name</code>
     * @api.access Read-write
     * @see Project.ProjectUpdater#setName(String)
     */
    @Override
    String getName();

    /**
     * @return The current owner of the project, may be null.
     * @api.field <code>owner</code>
     * @api.access Read-write
     * @see Project.ProjectUpdater#setOwner(User)
     */
    User getOwner();

    /**
     * @return A description of the project’s status containing a color (must be either null or one of: green,
     * yellow, red), a short description and an author.
     * @api.field <code>current_status</code>
     * @api.access Read-write
     * @see Project.ProjectUpdater#setStatus(ProjectStatus.Color, String)
     * @see ProjectStatus
     */
    ProjectStatus getCurrentStatus();

    /**
     * @return The day on which this project is due. This takes a date with format YYYY-MM-DD.
     * @api.field <code>due_date</code>
     * @api.access Read-write
     * @see Project.ProjectUpdater#setDueDate(String)
     */
    String getDueDate();

    // TODO add created_at and modified_at fields

    /**
     * @return True if the project is archived, false if not. Archived projects do not show in the UI by
     * default and may be treated differently for queries.
     * @api.field <code>archived</code>
     * @api.access Read-write
     * @see Project.ProjectUpdater#setArchived(boolean)
     */
    boolean isArchived();

    /**
     * @return True if the project is public to the organization. If false, do not share this project with
     * other users in this organization without explicitly checking to see if they have access.
     * @api.field <code>public</code>
     * @api.access Read-write
     * @see Project.ProjectUpdater#setPublic(boolean)
     */
    boolean isPublic();

    /**
     * @return Array of users who are members of this project.
     * @api.field <code>members</code>
     * @api.access Read-only
     * @see User
     */
    List<User> getMembers();

    // TODO add followers field

    /**
     * @return Color of the project. Must be one of the values from the
     * {@link Project.Color} enum.
     * @api.field <code>color</code>
     * @api.access Read-write
     * @see Project.ProjectUpdater#setColor(Color)
     * @see Project.Color
     */
    Color getColor();

    /**
     * @return More detailed, free-form textual information associated with the project.
     * @api.field <code>notes</code>
     * @api.access Read-write
     * @see Project.ProjectUpdater#setNotes(String)
     */
    String getNotes();

    /**
     * @return The workspace or organization this project is associated with. Once created, projects cannot be
     * moved to a different workspace. This attribute can only be specified at creation time.
     * @api.field <code>workspace</code>
     * @api.access Create-only
     * @see Workspace#createProject(String)
     * @see ru.jewelline.asana4j.api.clients.ProjectApiClient#create(long, String)
     * @see Workspace
     */
    Workspace getWorkspace();

    // TODO add team field

    /**
     * This method removes the project.
     * <br />
     * <i>Triggers HTTP communication with server</i>
     * @api.link <a href="https://asana.com/developers/api-reference/projects#delete">Delete a project</a>
     */
    void delete();

    /**
     * Starts an update process for the project.
     * @return A specific builder which allows to update required fields and save these changes (or discard).
     * @throws IllegalStateException if another update process is in progress
     * @api.linl <a href="https://asana.com/developers/api-reference/projects#update">Update a project</a>
     * @see Project.ProjectUpdater
     */
    ProjectUpdater startUpdate();

    /**
     * Returns list of tasks in the project
     * <br />
     * <i>Triggers HTTP communication with server</i>
     * @return Returns the compact task records for all tasks within the given project, ordered by their priority
     * within the project. Tasks can exist in more than one project at a time.
     * @api.link <a href="https://asana.com/developers/api-reference/projects#get-tasks">Get project tasks</a>
     * @see Task
     * @see ru.jewelline.asana4j.api.PagedList
     */
    PagedList<Task> getTasks();

    /**
     * Starts a creation process for the new task in the workspace.
     * @return A specific builder which allows to specify required fields for the new task.
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#create">Create a task</a>
     * @see Task
     * @see Task.TaskCreator
     */
    Task.TaskCreator createTask();

    /**
     * Enum which holds all available project colors.
     * @see Project#getColor()
     */
    enum Color {
        DARK_PINK("dark-pink"),
        DARK_GREEN("dark-green"),
        DARK_BLUE("dark-blue"),
        DARK_RED("dark-red"),
        DARK_TEAL("dark-teal"),
        DARK_BROWN("dark-brown"),
        DARK_ORANGE("dark-orange"),
        DARK_PURPLE("dark-purple"),
        DARK_WARM_GRAY("dark-warm-gray"),
        LIGHT_PINK("light-pink"),
        LIGHT_GREEN("light-green"),
        LIGHT_BLUE("light-blue"),
        LIGHT_RED("light-red"),
        LIGHT_TEAL("light-teal"),
        LIGHT_YELLOW("light-yellow"),
        LIGHT_ORANGE("light-orange"),
        LIGHT_PURPLE("light-purple"),
        LIGHT_WARM_GRAY("light-warm-gray"),
        NONE(null) {
            @Override
            public boolean isColorMatch(String colorCode) {
                return StringUtils.emptyOrOnlyWhiteSpace(colorCode);
            }
        },;

        private String colorCode;

        Color(String colorCode) {
            this.colorCode = colorCode;
        }

        /**
         * @return A string representation of color code, for example: <code>dark-pink</code> for {@link #DARK_PINK}
         * instance.
         */
        public String getColorCode() {
            return this.colorCode;
        }

        /**
         * Checks if the given value matches the color code of the {@link Color}
         * instance.
         * @param colorCode one of color codes or null.
         * @return <code>true</code> if the color code matches the one from the instance.
         * @see #getColorCode()
         */
        public boolean isColorMatch(String colorCode) {
            return this.colorCode.equalsIgnoreCase(colorCode);
        }

        @Override
        public String toString() {
            return getColorCode();
        }

        /**
         * Matches the <code>colorCode</code> parameter to one of instances from the {@link Color} enum
         * @param colorCode a string representation of project color or null
         * @return A {@link Project.Color} instance
         */
        public static Color getColorByCode(String colorCode) {
            for (Color color : Color.values()) {
                if (color.isColorMatch(colorCode)) {
                    return color;
                }
            }
            return Color.NONE;
        }

    }

    /**
     * A builder class which allows for user to set new values for project fields. Only touched fields will be
     * updated.
     * @see Project#startUpdate()
     */
    interface ProjectUpdater {

        /**
         * Sets a new name for the project.
         * @param name new name for the project, can be <code>null</code>.
         * @return The builder.
         * @see Project#getName()
         */
        ProjectUpdater setName(String name);

        /**
         * Sets a new owner for the project.
         * @param user new owner for the project, can be <code>null</code>.
         * @return The builder.
         * @see Project#getOwner()
         */
        ProjectUpdater setOwner(User user);

        /**
         * Sets description of the project's status.
         * @param color new project color.
         * @param text textual description for the new project state.
         * @return The builder.
         * @see Project#getCurrentStatus()
         * @see ProjectStatus.Color
         */
        ProjectUpdater setStatus(ProjectStatus.Color color, String text);

        /**
         * Sets the day on which this project is due.
         * @param date new due date, can be <code>null</code>.
         * @return The builder.
         * @see Project#getDueDate()
         */
        ProjectUpdater setDueDate(String date);

        /**
         * Sets the color of the project.
         * @param color new project color.
         * @return The builder.
         * @see Project#getColor()
         * @see Project.Color
         */
        ProjectUpdater setColor(Color color);

        /**
         * Sets a description for the project.
         * @param notes free-form textual information associated with the project.
         * @return The builder.
         * @see Project#getNotes()
         */
        ProjectUpdater setNotes(String notes);

        /**
         * Sets the archived status for the project.
         * @param isArchived <code>true</code> if the project is archived, <code>false</code> if not.
         * @return The builder.
         * @see Project#isArchived()
         */
        ProjectUpdater setArchived(boolean isArchived);

        /**
         * Sets the public status for the project.
         * @param isPublic <code>true</code> the project is public to the organization, <code>false</code> if not.
         * @return The builder.
         * @see Project#isPublic()
         */
        ProjectUpdater setPublic(boolean isPublic);

        /**
         * Dismisses changes for the project.
         * @return The project with original fields.
         * @see Project
         */
        Project abandon();

        /**
         * Applies changes for the project.
         * <br />
         * <i>Triggers HTTP communication with server</i>
         * @return The complete updated project record.
         * @api.linl <a href="https://asana.com/developers/api-reference/projects#update">Update a project</a>
         */
        Project update();
    }
}
