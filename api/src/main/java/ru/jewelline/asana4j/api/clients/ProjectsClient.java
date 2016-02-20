package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.asana4j.api.entities.Project;
import ru.jewelline.asana4j.api.entities.Task;
import ru.jewelline.request.http.modifiers.RequestModifier;

/**
 * Provides functionality for project management.
 */
public interface ProjectsClient {
    /**
     * Creates a new project in a workspace. Every project is required to be created in a specific workspace
     * or organization, and this cannot be changed once set.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param workspaceId the workspace to create the project in.
     * @param projectName name for the new project.
     * @return Returns the full record of the newly created project.
     * @api.link <a href="https://asana.com/developers/api-reference/projects#create">Create a project</a>
     * @see Project
     */
    Project create(long workspaceId, String projectName);

    /**
     * Creates a new project in a team. Every project is required to be created in a specific workspace
     * or organization, and this cannot be changed once set. If the workspace for your project is an organization,
     * you must also supply a team to share the project with.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param organisationId the workspace to create the project in.
     * @param teamId         the specific team to create the project in.
     * @param projectName    name for the new project.
     * @return Returns the full record of the newly created project.
     * @api.link <a href="https://asana.com/developers/api-reference/projects#create">Create a project</a>
     * @see Project
     */
    Project create(long organisationId, long teamId, String projectName);

    /**
     * Returns the complete project record for a single project.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param projectId        the project to get.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the complete project record for a single project.
     * @api.link <a href="https://asana.com/developers/api-reference/projects#get-single">Get single project</a>
     * @see Project
     * @see RequestModifier
     */
    Project getProjectById(long projectId, RequestModifier... requestModifiers);

    /**
     * Returns the compact task records for all tasks within the given project, ordered by their priority within
     * the project. Tasks can exist in more than one project at a time.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param projectId        the project in which to search for tasks.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the compact task records.
     * @api.link <a href="https://asana.com/developers/api-reference/projects#get-tasks">Get project tasks</a>
     * @see Task
     * @see RequestModifier
     */
    PagedList<Task> getProjectTasks(long projectId, RequestModifier... requestModifiers);

    /**
     * Returns compact records for all sections in the specified project.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param projectId        the project to get sections from.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns compact records for all sections in the specified project.
     * @api.link <a href="https://asana.com/developers/api-reference/projects#sections">Get project sections</a>
     * @see Task
     * @see RequestModifier
     */
    PagedList<Task> getProjectSections(long projectId, RequestModifier... requestModifiers);

    /**
     * Removes the specified project.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param projectId the project to delete.
     * @api.link <a href="https://asana.com/developers/api-reference/projects#delete">Get project sections</a>
     * @see RequestModifier
     */
    void deleteProject(long projectId);
}
