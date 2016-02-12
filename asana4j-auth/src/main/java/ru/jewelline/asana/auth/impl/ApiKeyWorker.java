package ru.jewelline.asana.auth.impl;

import ru.jewelline.asana.auth.AuthenticationException;
import ru.jewelline.asana.auth.AuthenticationProperty;

final class ApiKeyWorker extends AuthenticationWorker {

    private final Base64 base64;

    public ApiKeyWorker(AuthenticationServiceImpl authenticationService, Base64 base64) {
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
