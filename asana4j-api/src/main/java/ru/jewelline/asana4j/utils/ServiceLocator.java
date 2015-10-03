package ru.jewelline.asana4j.utils;

import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.http.HttpClient;

@Deprecated
// TODO remove it!!! Or not, still should think about it...
public interface ServiceLocator {
    HttpClient getHttpClient();
    URLBuilder getUrlBuilder();
    Base64 getBase64Tool();

    AuthenticationService getAuthenticationService();
}
