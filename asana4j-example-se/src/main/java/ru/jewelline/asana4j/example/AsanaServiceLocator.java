package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.auth.AuthenticationServiceImpl;
import ru.jewelline.asana4j.core.impl.http.HttpClientImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.se.Base64JavaSeUtil;
import ru.jewelline.asana4j.se.UrlBuilderJavaSeUtil;
import ru.jewelline.asana4j.utils.Base64;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.ServiceLocator;
import ru.jewelline.asana4j.utils.URLBuilder;

public class AsanaServiceLocator implements ServiceLocator {
    private final HttpClient httpClient;
    private final URLBuilder urlBuilder;
    private final Base64 base64;
    private final AuthenticationService authenticationService;
    private final PreferencesService preferencesService;


    public AsanaServiceLocator() {
        this.urlBuilder = new UrlBuilderJavaSeUtil();
        this.base64 = new Base64JavaSeUtil();
        this.preferencesService = new InMemoryPreferenceService();
        this.authenticationService = new AuthenticationServiceImpl(preferencesService, this);
        this.httpClient = new HttpClientImpl(this.urlBuilder, this.preferencesService);
    }

    @Override
    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    @Override
    public URLBuilder getUrlBuilder() {
        return this.urlBuilder;
    }

    @Override
    public Base64 getBase64Tool() {
        return this.base64;
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }
}
