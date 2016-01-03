package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.api.clients.ProjectApiClient;
import ru.jewelline.asana4j.api.clients.TaskApiClient;
import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.clients.ProjectApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.TaskApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.UserApiClientImpl;
import ru.jewelline.asana4j.core.impl.api.clients.WorkspaceApiClientImpl;
import ru.jewelline.asana4j.core.impl.auth.AuthenticationServiceImpl;
import ru.jewelline.asana4j.core.impl.http.HttpClientImpl;
import ru.jewelline.asana4j.core.impl.http.config.BaseHttpConfiguration;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.se.Base64JavaSeUtil;
import ru.jewelline.asana4j.se.UrlCreatorJavaSeUtil;
import ru.jewelline.asana4j.utils.Base64;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.URLCreator;

public class Asana {
    private final HttpClient httpClient;
    private final AuthenticationService authenticationService;
    private UserApiClientImpl userClient;
    private WorkspaceApiClientImpl workspaceClient;
    private ProjectApiClientImpl projectClient;
    private TaskApiClientImpl taskClient;

    public Asana() {
        URLCreator urlCreator = new UrlCreatorJavaSeUtil();
        Base64 base64 = new Base64JavaSeUtil();
        PreferencesService preferencesService = new InMemoryPreferenceService();
        this.httpClient = new HttpClientImpl(urlCreator, new BaseHttpConfiguration());
        this.authenticationService = new AuthenticationServiceImpl(preferencesService, this.httpClient, urlCreator, base64);
        this.userClient = new UserApiClientImpl(this.authenticationService, httpClient);
        this.workspaceClient = new WorkspaceApiClientImpl(this.authenticationService, this.httpClient);
        this.projectClient = new ProjectApiClientImpl(this.authenticationService, this.httpClient);
        this.taskClient = new TaskApiClientImpl(this.authenticationService, this.httpClient);
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
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
}
