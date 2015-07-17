package ru.jewelline.asana4j.core.impl.auth;

import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationProperties;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.utils.ServiceLocator;

public abstract class AuthenticationWorker {
    public static final String USER_OAUTH_ENDPOINT = "https://app.asana.com/-/oauth_authorize";
    public static final String ACCESS_TOKEN_ENDPOINT = "https://app.asana.com/-/oauth_token";

    private final ServiceLocator serviceLocator;

    public AuthenticationWorker(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    protected ServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    protected AuthenticationService getAuthenticationService(){
        return getServiceLocator().getAuthenticationService();
    }

    boolean isAuthenticated(){
        return getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.ACCESS_TOKEN) != null;
    }

    String getHeader(){
        String accessToken = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.ACCESS_TOKEN);
        if (accessToken != null){
            return  "Bearer " + accessToken;
        }
        return null;
    }

    abstract void authenticate() throws AuthenticationException;

    abstract String getOAuthUrl();

    abstract void parseOAuthResponse(String data);

    protected int getPropertyEndPosition(String data, int startPosition) {
        int accessTokenEndPosition = data.indexOf("&", startPosition);
        if (accessTokenEndPosition < 0) {
            accessTokenEndPosition = data.length();
        }
        return accessTokenEndPosition;
    }

    protected String getProperty(String data, String propertyMarker){
        int propertyMarkerPosition = data.indexOf(propertyMarker);
        if (propertyMarkerPosition >= 0){
            int propertyStartPosition = propertyMarkerPosition + propertyMarker.length();
            return data.substring(propertyStartPosition, getPropertyEndPosition(data, propertyStartPosition));
        }
        return null;
    }

    protected String getClientIdOrThrowException() {
        String clientId = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.CLIENT_ID);
        if (clientId == null){
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.CLIENT_ID' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object).");
        }
        return clientId;
    }

    protected String  getClientSecretOrThrowException() {
        String clientSecret = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.CLIENT_SECRET);
        if (clientSecret == null){
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.CLIENT_SECRET' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object).");
        }
        return clientSecret;
    }

    protected String getRedirectUrlOrTrowException() {
        String redirectUrl = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.AUTHORIZATION_ENDPOINT_REDIRECT_URL);
        if (redirectUrl == null){
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationService.AUTHORIZATION_ENDPOINT_REDIRECT_URL' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object).");
        }
        return redirectUrl;
    }

    protected String getAccessCodeOrThrowException() {
        String accessCode = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.ACCESS_CODE);
        if (accessCode == null){
            throw new AuthenticationException(AuthenticationException.NOT_ENOUGH_INFO_FOR_AUTHENTICATION,
                    "The property 'AuthenticationType.Properties.ACCESS_CODE' must be specified, see Java doc for " +
                            "AuthenticationService#setAuthenticationProperty(String, Object). " +
                            "You can find more information here: " +
                            "https://asana.com/developers/documentation/getting-started/authentication#AsanaConnect");
        }
        return accessCode;
    }

    protected void logout(){
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.ACCESS_CODE, null);
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.ACCESS_TOKEN, null);
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.REFRESH_TOKEN, null);
    }
}
