package ru.jewelline.asana4j.core.impl.auth;

import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationProperty;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.UrlBuilder;

final class GrantImplicitWorker extends AuthenticationWorker {

    private final HttpRequestFactory httpRequestFactory;

    public GrantImplicitWorker(AuthenticationServiceImpl authenticationService, HttpRequestFactory httpRequestFactory) {
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
                .setQueryParameter("client_id", clientId)
                .setQueryParameter("redirect_uri", redirectUrl)
                .setQueryParameter("response_type", "token");
        String appState = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.AUTHORIZATION_ENDPOINT_STATE);
        if (appState != null) {
            urlBuilder.setQueryParameter("state", appState);
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
        getAuthenticationService().setAuthenticationProperty(AuthenticationType.Properties.ACCESS_TOKEN,
                getProperty(data, "data="));
        */
    }
}
