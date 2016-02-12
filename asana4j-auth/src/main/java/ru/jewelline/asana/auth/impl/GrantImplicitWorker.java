package ru.jewelline.asana.auth.impl;

import ru.jewelline.asana.auth.AuthenticationException;
import ru.jewelline.asana.auth.AuthenticationProperty;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.UrlBuilder;

final class GrantImplicitWorker extends AuthenticationWorker {

    private final HttpRequestFactory httpRequestFactory;

    GrantImplicitWorker(AuthenticationServiceImpl authenticationService, HttpRequestFactory httpRequestFactory) {
        super(authenticationService);
        this.httpRequestFactory = httpRequestFactory;
    }

    void authenticate() throws AuthenticationException {
        // do nothing, just check that we have all required properties and throw exception if not
        String accessToken = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN);
        if (accessToken == null) {
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.ACCESS_TOKEN' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, String). " +
                            "You can find more information here: " +
                            "https://asana.com/developers/documentation/getting-started/auth#implicit-grant");
        }
    }

    @Override
    String getOAuthUrl() {
        String clientId = getClientIdOrThrowException();
        String redirectUrl = getRedirectUrlOrTrowException();
        UrlBuilder urlBuilder = this.httpRequestFactory.urlBuilder()
                .path(USER_OAUTH_ENDPOINT)
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("redirect_uri", redirectUrl)
                .addQueryParameter("response_type", "token");
        String appState = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.AUTHORIZATION_ENDPOINT_STATE);
        if (appState != null) {
            urlBuilder.addQueryParameter("state", appState);
        }
        return urlBuilder.build();
    }

    @Override
    void parseOAuthResponse(String data) {
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN,
                getProperty(data, "access_token="));
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.TOKEN_TYPE,
                getProperty(data, "token_type="));
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.EXPIRES_IN,
                getProperty(data, "expires_in="));
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.AUTHORIZATION_ENDPOINT_STATE,
                getProperty(data, "state="));
        /*
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN,
                getProperty(data, "data="));
        */
    }
}
