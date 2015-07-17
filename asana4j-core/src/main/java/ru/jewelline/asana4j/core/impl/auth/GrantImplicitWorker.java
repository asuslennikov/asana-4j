package ru.jewelline.asana4j.core.impl.auth;

import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationProperties;
import ru.jewelline.asana4j.utils.ServiceLocator;
import ru.jewelline.asana4j.utils.URLBuilder;

public class GrantImplicitWorker extends AuthenticationWorker {

    public GrantImplicitWorker(ServiceLocator serviceLocator) {
        super(serviceLocator);
    }

    void authenticate() throws AuthenticationException {
        // do nothing, just check that we have all required properties and throw exception if not
        String accessToken = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.ACCESS_TOKEN);
        if (accessToken == null){
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.ACCESS_TOKEN' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, String). " +
                            "You can find more information here: " +
                            "https://asana.com/developers/documentation/getting-started/authentication#AsanaConnect");
        }
    }

    @Override
    String getOAuthUrl() {
        String clientId = getClientIdOrThrowException();
        String redirectUrl = getRedirectUrlOrTrowException();
        URLBuilder urlBuilder = getServiceLocator().getUrlBuilder()
                .path(USER_OAUTH_ENDPOINT)
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("redirect_uri", redirectUrl)
                .addQueryParameter("response_type", "token");
        String appState = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.AUTHORIZATION_ENDPOINT_STATE);
        if (appState != null){
            urlBuilder.addQueryParameter("state", appState);
        }
        return  urlBuilder.build();
    }

    @Override
    void parseOAuthResponse(String data) {
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.ACCESS_TOKEN,
                getProperty(data, "#access_token="));
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.TOKEN_TYPE,
                getProperty(data, "token_type="));
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.EXPIRES_IN,
                getProperty(data, "expires_in="));
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.AUTHORIZATION_ENDPOINT_STATE,
                getProperty(data, "state="));
        /*
        getAuthenticationService().setAuthenticationProperty(AuthenticationType.Properties.ACCESS_TOKEN,
                getProperty(data, "data="));
        */
    }
}
