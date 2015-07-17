package ru.jewelline.asana4j.core.impl.auth;

import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationProperties;
import ru.jewelline.asana4j.utils.ServiceLocator;

public class BasicAuthenticationWorker extends AuthenticationWorker {

    public BasicAuthenticationWorker(ServiceLocator serviceLocator) {
        super(serviceLocator);
    }

    @Override
    boolean isAuthenticated() {
        return getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.API_KEY) != null;
    }

    @Override
    public String getHeader() {
        String apiKey = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.API_KEY);
        if (apiKey != null){
            return  "Basic " + getServiceLocator().getBase64Tool().encode(apiKey + ":");
        }
        return null;
    }

    void authenticate() throws AuthenticationException {
        // do nothing, just check that we have all required properties and throw exception if not
        if (!isAuthenticated()){
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.API_KEY' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object). " +
                            "You can find more information here: " +
                            "https://asana.com/developers/documentation/getting-started/authentication#api_keys");
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
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.API_KEY, null);
    }
}
