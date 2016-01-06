package ru.jewelline.asana4j.api.entity;

import ru.jewelline.asana4j.api.PagedList;

/**
 * A workspace is the highest-level organizational unit in Asana. All projects and tasks have an associated workspace.
 * <p>
 * An organization is a special kind of workspace that represents a company. In an organization, you can group your
 * projects into teams.
 *
 * @api.link <a href="https://asana.com/developers/api-reference/workspaces">Workspaces API page</a>
 * @see ru.jewelline.asana4j.api.entity.HasId
 * @see ru.jewelline.asana4j.api.entity.HasName
 */
public interface Workspace extends HasId, HasName {

    /**
     * @return Whether the workspace is an organization.
     * @api.field <code>is_organization</code>
     * @api.access Read-only
     */
    boolean isOrganisation();

    /**
     * @return The name of the workspace.
     * @api.field <code>is_organization</code>
     * @api.access Read-write
     * @see #setName(String)
     */
    @Override
    String getName();

    /**
     * Set for the workspace a new name, but it doesn't actually trigger a connection to the API server. You must call
     * the {@link #update()} method for saving.
     * @param name a new name of workspace. Can be null
     */
    void setName(String name);

    /**
     * Grants access for user for this workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     * @param userId user unique identifier, see {@link User#getId()}
     * @return the full user record for the invited user
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #addUser(String)
     * @see #addCurrentUser()
     * @see User
     */
    User addUser(long userId);

    /**
     * Grants access for user for this workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     * @param email user email address, see {@link User#getEmail()}
     * @return the full user record for the invited user
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #addUser(long)
     * @see #addCurrentUser()
     * @see User
     */
    User addUser(String email);

    /**
     * Grants access for the current authenticated user for this workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     * @return the full user record for the invited user
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #addUser(long)
     * @see #addUser(String)
     * @see User
     */
    User addCurrentUser();

    /**
     * Revoke access for user for this workspace. The user making this call must be an admin in the workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     * @param userId user unique identifier, see {@link User#getId()}
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #removeUser(long)
     * @see #removeCurrentUser()
     */
    void removeUser(long userId);

    /**
     * Revoke access for user for this workspace. The user making this call must be an admin in the workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     * @param email user email address, see {@link User#getEmail()}
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #removeUser(String)
     * @see #removeCurrentUser()
     */
    void removeUser(String email);

    /**
     * Revoke access for the current authenticated user for this workspace. The user making this call must be
     * an admin in the workspace
     * <p><i>Triggers HTTP communication with server</i></p>
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #removeUser(long)
     * @see #removeUser(String)
     */
    void removeCurrentUser();

    /**
     * Applies current changes in the workspace. Currently the only field that can be modified for a workspace is
     * its name (see {@link #setName(String)}).
     * <p><i>Triggers HTTP communication with server</i></p>
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#update">Update a workspace</a>
     */
    void update();

    /**
     * Returns a list of projects which belong to this workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     * @return the compact project records for some filtered set of projects
     * @api.link <a href="https://asana.com/developers/api-reference/projects#query">Query for projects</a>
     * @see Project
     * @see PagedList
     */
    PagedList<Project> getProjects();

    /**
     * Creates a new project in the workspace or team (workspace cannot be changed once set). If the workspace for your
     * project is an organization (see {@link #isOrganisation()}), you must also supply a team to share the project with.
     * <p><i>Triggers HTTP communication with server</i></p>
     * @param name a display name of project
     * @return the full record of the newly created project.
     * @api.link <a href="https://asana.com/developers/api-reference/projects#create">Create a project</a>
     * @see Project
     */
    // TODO add team support (the must parameter for organisation, see javadoc)
    Project createProject(String name);

    /**
     * Starts a new task creation process. Every task is required to be created in a specific workspace, and this
     * workspace cannot be changed once set.
     * @return A builder which allows for user to specify parameters for the new task and complete the task creation process
     * (workspace id is already set, see {@link ru.jewelline.asana4j.api.entity.Task.TaskCreator#setWorkspace(long)} and
     * {@link #getId()}).
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#create">Create a task</a>
     * @see Task
     * @see ru.jewelline.asana4j.api.entity.Task.TaskCreator
     */
    Task.TaskCreator createTask();
}
