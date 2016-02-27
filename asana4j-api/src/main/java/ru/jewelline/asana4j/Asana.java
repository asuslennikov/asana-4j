package ru.jewelline.asana4j;

import ru.jewelline.asana4j.api.clients.AttachmentApiClient;
import ru.jewelline.asana4j.api.clients.ProjectApiClient;
import ru.jewelline.asana4j.api.clients.StoryApiClient;
import ru.jewelline.asana4j.api.clients.TagApiClient;
import ru.jewelline.asana4j.api.clients.TaskApiClient;
import ru.jewelline.asana4j.api.clients.TeamClientApi;
import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.utils.PropertiesStore;

/**
 * The entry point for Asana API, provides access for API clients and services.
 *
 * @see PropertiesStore
 * @see AuthenticationService
 * @see AttachmentApiClient
 * @see ProjectApiClient
 * @see StoryApiClient
 * @see TaskApiClient
 * @see UserApiClient
 * @see WorkspaceApiClient
 */
public interface Asana {

    AuthenticationService getAuthenticationService();

    UserApiClient getUserClient();

    WorkspaceApiClient getWorkspaceClient();

    ProjectApiClient getProjectClient();

    TaskApiClient getTaskClient();

    StoryApiClient getStoryClient();

    AttachmentApiClient getAttachmentClient();

    TeamClientApi getTeamClient();

    TagApiClient getTagClient();
}