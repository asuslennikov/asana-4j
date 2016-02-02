package ru.jewelline.asana4j.core.impl;

import ru.jewelline.asana4j.Asana;
import ru.jewelline.asana4j.api.clients.AttachmentApiClient;
import ru.jewelline.asana4j.api.clients.ProjectApiClient;
import ru.jewelline.asana4j.api.clients.StoryApiClient;
import ru.jewelline.asana4j.api.clients.TagApiClient;
import ru.jewelline.asana4j.api.clients.TaskApiClient;
import ru.jewelline.asana4j.api.clients.TeamClientApi;
import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.RequestFactoryImpl;
import ru.jewelline.asana4j.core.impl.api.clients.AttachmentApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.ProjectApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.StoryApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.TagApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.TaskApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.TeamApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.UserApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.WorkspaceApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.auth.AuthenticationServiceImpl;
import ru.jewelline.asana4j.core.impl.http.HttpRequestFactoryImpl;
import ru.jewelline.asana4j.core.impl.http.config.BaseHttpConfiguration;
import ru.jewelline.asana4j.utils.Base64;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.URLCreator;
import ru.jewelline.request.http.HttpRequestFactory;

public abstract class AsanaImpl implements Asana {

    private final PreferencesService preferencesService;

    private AuthenticationService authenticationService;
    private UserApiClient userClient;
    private WorkspaceApiClient workspaceClient;
    private ProjectApiClient projectClient;
    private TaskApiClient taskClient;
    private StoryApiClient storyClient;
    private AttachmentApiClient attachmentClient;
    private TeamClientApi teamClient;
    private TagApiClient tagClient;

    public AsanaImpl(PreferencesService preferencesService, URLCreator urlCreator, Base64 base64) {
        this.preferencesService = preferencesService;

        HttpRequestFactory httpRequestFactory = new HttpRequestFactoryImpl(urlCreator, new BaseHttpConfiguration());
        this.authenticationService = new AuthenticationServiceImpl(preferencesService, httpRequestFactory, urlCreator, base64);
        RequestFactory requestFactory = new RequestFactoryImpl(httpRequestFactory, this.authenticationService);
        ApiEntityContext entityContext = new ApiEntityContext(requestFactory);
        this.userClient = new UserApiClientImpl(requestFactory, entityContext);
        this.workspaceClient = new WorkspaceApiClientImpl(requestFactory, entityContext);
        this.projectClient = new ProjectApiClientImpl(requestFactory, entityContext);
        this.taskClient = new TaskApiClientImpl(requestFactory, entityContext);
        this.storyClient = new StoryApiClientImpl(requestFactory, entityContext);
        this.attachmentClient = new AttachmentApiClientImpl(requestFactory, entityContext);
        this.teamClient = new TeamApiClientImpl(requestFactory, entityContext);
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
    public UserApiClient getUserClient() {
        return this.userClient;
    }

    @Override
    public WorkspaceApiClient getWorkspaceClient() {
        return this.workspaceClient;
    }

    @Override
    public ProjectApiClient getProjectClient() {
        return this.projectClient;
    }

    @Override
    public TaskApiClient getTaskClient() {
        return this.taskClient;
    }

    @Override
    public StoryApiClient getStoryClient() {
        return this.storyClient;
    }

    @Override
    public AttachmentApiClient getAttachmentClient() {
        return this.attachmentClient;
    }

    @Override
    public TeamClientApi getTeamClient() {
        return this.teamClient;
    }

    @Override
    public TagApiClient getTagClient() {
        return this.tagClient;
    }
}
