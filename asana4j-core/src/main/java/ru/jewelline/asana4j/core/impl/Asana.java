package ru.jewelline.asana4j.core.impl;

import ru.jewelline.asana4j.api.clients.AttachmentApiClient;
import ru.jewelline.asana4j.api.clients.ProjectApiClient;
import ru.jewelline.asana4j.api.clients.StoryApiClient;
import ru.jewelline.asana4j.api.clients.TaskApiClient;
import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.RequestFactoryImpl;
import ru.jewelline.asana4j.core.impl.api.clients.AttachmentApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.ProjectApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.StoryApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.TaskApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.UserApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.WorkspaceApiClientImpl;
import ru.jewelline.asana4j.core.impl.auth.AuthenticationServiceImpl;
import ru.jewelline.asana4j.core.impl.http.HttpClientImpl;
import ru.jewelline.asana4j.core.impl.http.config.BaseHttpConfiguration;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.utils.Base64;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.URLCreator;

public abstract class Asana {

    private final PreferencesService preferencesService;
    private final URLCreator urlCreator;
    private final Base64 base64;

    private AuthenticationService authenticationService;
    private UserApiClient userClient;
    private WorkspaceApiClient workspaceClient;
    private ProjectApiClient projectClient;
    private TaskApiClient taskClient;
    private StoryApiClient storyClient;
    private AttachmentApiClient attachmentClient;

    public Asana(PreferencesService preferencesService, URLCreator urlCreator, Base64 base64) {
        this.preferencesService = preferencesService;
        this.urlCreator = urlCreator;
        this.base64 = base64;

        HttpClient httpClient = new HttpClientImpl(urlCreator, new BaseHttpConfiguration());
        this.authenticationService = new AuthenticationServiceImpl(preferencesService, httpClient, this.urlCreator, this.base64);
        RequestFactory requestFactory = new RequestFactoryImpl(httpClient, this.authenticationService);
        this.userClient = new UserApiClientImpl(requestFactory);
        this.workspaceClient = new WorkspaceApiClientImpl(requestFactory);
        this.projectClient = new ProjectApiClientImpl(requestFactory);
        this.taskClient = new TaskApiClientImpl(requestFactory);
        this.storyClient = new StoryApiClientImpl(requestFactory);
        this.attachmentClient = new AttachmentApiClientImpl(requestFactory);
    }

    public PreferencesService getPreferencesService() {
        return preferencesService;
    }

    public AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }

    public UserApiClient getUserClient() {
        return this.userClient;
    }

    public WorkspaceApiClient getWorkspaceClient() {
        return this.workspaceClient;
    }

    public ProjectApiClient getProjectClient() {
        return this.projectClient;
    }

    public TaskApiClient getTaskClient() {
        return this.taskClient;
    }

    public StoryApiClient getStoryClient() {
        return this.storyClient;
    }

    public AttachmentApiClient getAttachmentClient() {
        return this.attachmentClient;
    }
}
