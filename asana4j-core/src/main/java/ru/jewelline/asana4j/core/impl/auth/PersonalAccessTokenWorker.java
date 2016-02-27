package ru.jewelline.asana4j.core.impl.auth;

import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationProperty;

final class PersonalAccessTokenWorker extends AuthenticationWorker {

    public PersonalAccessTokenWorker(AuthenticationServiceImpl authenticationService) {
        super(authenticationService);
    }

    @Override
    boolean isAuthenticated() {
        return getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN) != null;
    }

    @Override
    public String getHeader() {
        String accessToken = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN);
        if (accessToken != null) {
            return "Bearer " + accessToken;
        }
        return null;
    }

    void authenticate() throws AuthenticationException {
        // do nothing, just check that we have all required properties and throw exception if not
        if (!isAuthenticated()) {
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.ACCESS_TOKEN' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object). " +
                            "You can find more information here: " +
                            "https://asana.com/developers/documentation/getting-started/auth#personal-access-token");
        }
    }

    @Override
    String getOAuthUrl() {
        // This authentication type doesn't require user credentials
        return null;
    }

    @Override
    void parseOAuthResponse(String data) {
        // do nothing, this authentication type doesn't require OAuth, so nothing useful in this data
    }
}
