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
import ru.jewelline.asana4j.utils.Base64;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.URLCreator;
import ru.jewelline.request.http.HttpRequestFactories;
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

        HttpRequestFactory httpRequestFactory = HttpRequestFactories.standard();
        this.authenticationService = new AuthenticationServiceImpl(preferencesService, httpRequestFactory, urlCreator, base64);
        ApiEntityContext entityContext = new ApiEntityContext(httpRequestFactory, this.authenticationService);
        this.userClient = new UserApiClientImpl(httpRequestFactory, entityContext);
        this.workspaceClient = new WorkspaceApiClientImpl(httpRequestFactory, entityContext);
        this.projectClient = new ProjectApiClientImpl(httpRequestFactory, entityContext);
        this.taskClient = new TaskApiClientImpl(httpRequestFactory, entityContext);
        this.storyClient = new StoryApiClientImpl(httpRequestFactory, entityContext);
        this.attachmentClient = new AttachmentApiClientImpl(httpRequestFactory, entityContext);
        this.teamClient = new TeamApiClientImpl(httpRequestFactory, entityContext);
        this.tagClient = new TagApiClientImpl(httpRequestFactory, entityContext);
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
