package ru.jewelline.asana4j.core.impl;

import ru.jewelline.asana4j.Asana;
import ru.jewelline.asana4j.api.clients.AttachmentsClient;
import ru.jewelline.asana4j.api.clients.ProjectsClient;
import ru.jewelline.asana4j.api.clients.StoriesClient;
import ru.jewelline.asana4j.api.clients.TagsClient;
import ru.jewelline.asana4j.api.clients.TasksClient;
import ru.jewelline.asana4j.api.clients.TeamsClient;
import ru.jewelline.asana4j.api.clients.UsersClient;
import ru.jewelline.asana4j.api.clients.WorkspacesClient;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.clients.AttachmentsClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.ProjectsClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.StoriesClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.TagsClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.TasksClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.TeamsApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.UsersClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.WorkspacesClientImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.auth.AuthenticationServiceImpl;
import ru.jewelline.asana4j.utils.Base64;
import ru.jewelline.request.http.HttpRequestFactories;
import ru.jewelline.request.http.HttpRequestFactory;

public abstract class AsanaImpl implements Asana {

    private AuthenticationService authenticationService;
    private UsersClient usersClient;
    private WorkspacesClient workspacesClient;
    private ProjectsClient projectsClient;
    private TasksClient tasksClient;
    private StoriesClient storiesClient;
    private AttachmentsClient attachmentsClient;
    private TeamsClient teamsClient;
    private TagsClient tagsClient;

    public AsanaImpl() {
        HttpRequestFactory httpRequestFactory = HttpRequestFactories.standard();
        this.authenticationService = new AuthenticationServiceImpl(httpRequestFactory, getBase64Tool());
        ApiEntityContext entityContext = new ApiEntityContext(httpRequestFactory, this.authenticationService);

        this.usersClient = new UsersClientImpl(httpRequestFactory, entityContext);
        this.workspacesClient = new WorkspacesClientImpl(httpRequestFactory, entityContext);
        this.projectsClient = new ProjectsClientImpl(httpRequestFactory, entityContext);
        this.tasksClient = new TasksClientImpl(httpRequestFactory, entityContext);
        this.storiesClient = new StoriesClientImpl(httpRequestFactory, entityContext);
        this.attachmentsClient = new AttachmentsClientImpl(httpRequestFactory, entityContext);
        this.teamsClient = new TeamsApiClientImpl(httpRequestFactory, entityContext);
        this.tagsClient = new TagsClientImpl(httpRequestFactory, entityContext);
    }

    public abstract Base64 getBase64Tool();

    @Override
    public AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }

    @Override
    public UsersClient getUsersClient() {
        return this.usersClient;
    }

    @Override
    public WorkspacesClient getWorkspacesClient() {
        return this.workspacesClient;
    }

    @Override
    public ProjectsClient getProjectsClient() {
        return this.projectsClient;
    }

    @Override
    public TasksClient getTasksClient() {
        return this.tasksClient;
    }

    @Override
    public StoriesClient getStoriesClient() {
        return this.storiesClient;
    }

    @Override
    public AttachmentsClient getAttachmentsClient() {
        return this.attachmentsClient;
    }

    @Override
    public TeamsClient getTeamsClient() {
        return this.teamsClient;
    }

    @Override
    public TagsClient getTagsClient() {
        return this.tagsClient;
    }
}
