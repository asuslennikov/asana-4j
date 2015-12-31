package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.auth.AuthenticationService;
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

public class AsanaContext {
    private final HttpClient httpClient;
    private final URLCreator urlCreator;
    private final Base64 base64;
    private final AuthenticationService authenticationService;
    private final PreferencesService preferencesService;

    public AsanaContext() {
        this.urlCreator = new UrlCreatorJavaSeUtil();
        this.base64 = new Base64JavaSeUtil();
        this.preferencesService = new InMemoryPreferenceService();
        this.httpClient = new HttpClientImpl(this.urlCreator, new BaseHttpConfiguration());
        this.authenticationService = new AuthenticationServiceImpl(preferencesService, httpClient, urlCreator, base64);
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }

    public UserApiClient getUserClient() {
        return new UserApiClientImpl(this.authenticationService, httpClient);
    }

    public WorkspaceApiClient getWorkspaceClient() {
        return new WorkspaceApiClientImpl(this.authenticationService, this.httpClient);
    }
}
