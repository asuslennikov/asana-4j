package ru.jewelline.asana4j.core.impl.auth;

import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationProperty;
import ru.jewelline.asana4j.utils.Base64;

final class BasicAuthenticationWorker extends AuthenticationWorker {

    private final Base64 base64;

    public BasicAuthenticationWorker(AuthenticationServiceImpl authenticationService, Base64 base64) {
        super(authenticationService);
        this.base64 = base64;
    }

    @Override
    boolean isAuthenticated() {
        return getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.API_KEY) != null;
    }

    @Override
    public String getHeader() {
        String apiKey = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.API_KEY);
        if (apiKey != null) {
            return "Basic " + this.base64.encode(apiKey + ':');
        }
        return null;
    }

    void authenticate() throws AuthenticationException {
        // do nothing, just check that we have all required properties and throw exception if not
        if (!isAuthenticated()) {
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.API_KEY' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object). " +
                            "You can find more information here: " +
                            "https://asana.com/developers/documentation/getting-started/auth#api-key");
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

    @Override
    protected void logout() {
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.API_KEY, null);
    }
}
