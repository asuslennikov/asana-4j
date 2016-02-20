package ru.jewelline.asana4j.impl;

import ru.jewelline.asana4j.Asana;
import ru.jewelline.asana4j.api.clients.AttachmentsClient;
import ru.jewelline.asana4j.api.clients.ProjectsClient;
import ru.jewelline.asana4j.api.clients.StoriesClient;
import ru.jewelline.asana4j.api.clients.TagsClient;
import ru.jewelline.asana4j.api.clients.TaskApiClient;
import ru.jewelline.asana4j.api.clients.TeamsClient;
import ru.jewelline.asana4j.api.clients.UsersClient;
import ru.jewelline.asana4j.api.clients.WorkspacesClient;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.RequestFactoryImpl;
import ru.jewelline.asana4j.core.impl.auth.AuthenticationServiceImpl;
import ru.jewelline.asana4j.core.impl.http.HttpRequestFactoryImpl;
import ru.jewelline.asana4j.core.impl.http.config.BaseHttpConfiguration;
import ru.jewelline.asana4j.impl.clients.AttachmentsClientImpl;
import ru.jewelline.asana4j.impl.clients.ProjectsClientImpl;
import ru.jewelline.asana4j.impl.clients.StoriesClientImpl;
import ru.jewelline.asana4j.impl.clients.TagApiClientImpl;
import ru.jewelline.asana4j.impl.clients.TaskApiClientImpl;
import ru.jewelline.asana4j.impl.clients.TeamsApiClientImpl;
import ru.jewelline.asana4j.impl.clients.UsersClientImpl;
import ru.jewelline.asana4j.impl.clients.WorkspacesClientImpl;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.utils.Base64;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.URLCreator;
import ru.jewelline.request.http.HttpRequestFactory;

public abstract class AsanaImpl implements Asana {

    private final PreferencesService preferencesService;

    private AuthenticationService authenticationService;
    private UsersClient userClient;
    private WorkspacesClient workspaceClient;
    private ProjectsClient projectClient;
    private TaskApiClient taskClient;
    private StoriesClient storyClient;
    private AttachmentsClient attachmentClient;
    private TeamsClient teamClient;
    private TagsClient tagClient;

    public AsanaImpl(PreferencesService preferencesService, URLCreator urlCreator, Base64 base64) {
        this.preferencesService = preferencesService;

        HttpRequestFactory httpRequestFactory = new HttpRequestFactoryImpl(urlCreator, new BaseHttpConfiguration());
        this.authenticationService = new AuthenticationServiceImpl(preferencesService, httpRequestFactory, urlCreator, base64);
        RequestFactory requestFactory = new RequestFactoryImpl(httpRequestFactory, this.authenticationService);
        ApiEntityContext entityContext = new ApiEntityContext(requestFactory);
        this.userClient = new UsersClientImpl(requestFactory, entityContext);
        this.workspaceClient = new WorkspacesClientImpl(requestFactory, entityContext);
        this.projectClient = new ProjectsClientImpl(requestFactory, entityContext);
        this.taskClient = new TaskApiClientImpl(requestFactory, entityContext);
        this.storyClient = new StoriesClientImpl(requestFactory, entityContext);
        this.attachmentClient = new AttachmentsClientImpl(requestFactory, entityContext);
        this.teamClient = new TeamsApiClientImpl(requestFactory, entityContext);
        this.tagClient = new TagApiClientImpl(requestFactory, entityContext);
    }

    @Override
    public PreferencesService getPreferencesService() {
        return preferencesService;
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }

    @Override
    public UsersClient getUserClient() {
        return this.userClient;
    }

    @Override
    public WorkspacesClient getWorkspaceClient() {
        return this.workspaceClient;
    }

    @Override
    public ProjectsClient getProjectClient() {
        return this.projectClient;
    }

    @Override
    public TaskApiClient getTaskClient() {
        return this.taskClient;
    }

    @Override
    public StoriesClient getStoryClient() {
        return this.storyClient;
    }

    @Override
    public AttachmentsClient getAttachmentClient() {
        return this.attachmentClient;
    }

    @Override
    public TeamsClient getTeamClient() {
        return this.teamClient;
    }

    @Override
    public TagsClient getTagClient() {
        return this.tagClient;
    }
}
