package ru.jewelline.asana.auth.impl;

import ru.jewelline.asana.auth.AuthenticationException;
import ru.jewelline.asana.auth.AuthenticationProperty;
import ru.jewelline.asana.auth.AuthenticationService;
import ru.jewelline.asana.core.utils.StringUtils;

abstract class AuthenticationWorker {
    public static final String USER_OAUTH_ENDPOINT = "https://app.asana.com/-/oauth_authorize";
    public static final String ACCESS_TOKEN_ENDPOINT = "https://app.asana.com/-/oauth_token";

    private AuthenticationServiceImpl authenticationService;

    AuthenticationWorker(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    protected AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }

    boolean isAuthenticated() {
        return getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN) != null;
    }

    String getHeader() {
        String accessToken = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN);
        if (accessToken != null) {
            return "Bearer " + accessToken;
        }
        return null;
    }

    abstract void authenticate() throws AuthenticationException;

    abstract String getOAuthUrl();

    abstract void parseOAuthResponse(String data);

    protected String getProperty(String data, String propertyMarker) {
        return StringUtils.getSubstring(data, propertyMarker, "&");
    }

    protected String getClientIdOrThrowException() {
        String clientId = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.CLIENT_ID);
        if (clientId == null) {
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.CLIENT_ID' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object).");
        }
        return clientId;
    }

    protected String getClientSecretOrThrowException() {
        String clientSecret = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.CLIENT_SECRET);
        if (clientSecret == null) {
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.CLIENT_SECRET' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object).");
        }
        return clientSecret;
    }

    protected String getRedirectUrlOrTrowException() {
        String redirectUrl = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.AUTHORIZATION_ENDPOINT_REDIRECT_URL);
        if (redirectUrl == null) {
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationService.AUTHORIZATION_ENDPOINT_REDIRECT_URL' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object).");
        }
        return redirectUrl;
    }

    protected String getAccessCodeOrThrowException() {
        String accessCode = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.ACCESS_CODE);
        if (accessCode == null) {
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.ACCESS_CODE' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object). " +
                            "You can find more information here: " +
                            "https://asana.com/developers/documentation/getting-started/authentication#AsanaConnect");
        }
        return accessCode;
    }

    protected void logout() {
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.ACCESS_CODE, null);
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN, null);
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.REFRESH_TOKEN, null);
    }
}
