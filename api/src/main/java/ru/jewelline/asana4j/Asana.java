package ru.jewelline.asana4j;

import ru.jewelline.asana4j.api.clients.AttachmentsClient;
import ru.jewelline.asana4j.api.clients.ProjectsClient;
import ru.jewelline.asana4j.api.clients.StoriesClient;
import ru.jewelline.asana4j.api.clients.TagsClient;
import ru.jewelline.asana4j.api.clients.TasksClient;
import ru.jewelline.asana4j.api.clients.TeamsClient;
import ru.jewelline.asana4j.api.clients.UsersClient;
import ru.jewelline.asana4j.api.clients.WorkspacesClient;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.utils.PropertiesStore;

/**
 * The entry point for Asana API, provides access for API clients and services.
 *
 * @see PropertiesStore
 * @see AuthenticationService
 * @see AttachmentsClient
 * @see ProjectsClient
 * @see StoriesClient
 * @see TasksClient
 * @see UsersClient
 * @see WorkspacesClient
 */
public interface Asana {

    AuthenticationService getAuthenticationService();

    UsersClient getUsersClient();

    WorkspacesClient getWorkspacesClient();

    ProjectsClient getProjectsClient();

    TasksClient getTasksClient();

    StoriesClient getStoriesClient();

    AttachmentsClient getAttachmentsClient();

    TeamsClient getTeamsClient();

    TagsClient getTagsClient();
}