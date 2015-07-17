package ru.jewelline.asana4j.utils;

import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.http.HttpClient;

public interface ServiceLocator {
    HttpClient getHttpClient();
    URLBuilder getUrlBuilder();
    Base64 getBase64Tool();

    AuthenticationService getAuthenticationService();
    PreferencesService getPreferencesService();
}
