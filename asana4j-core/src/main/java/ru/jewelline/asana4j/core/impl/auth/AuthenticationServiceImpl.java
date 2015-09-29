package ru.jewelline.asana4j.core.impl.auth;

import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.auth.AuthenticationType;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.ServiceLocator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is not thread safe.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Map<AuthenticationType, AuthenticationWorker> authenticationWorkers;
    private final PreferencesService preferencesService;

    private volatile AuthenticationType authenticationType;

    public AuthenticationServiceImpl(PreferencesService preferencesService, ServiceLocator serviceLocator) {
        this.preferencesService = preferencesService;
        this.authenticationWorkers = new HashMap<>();
        this.authenticationWorkers.put(AuthenticationType.BASIC, new BasicAuthenticationWorker(serviceLocator));
        this.authenticationWorkers.put(AuthenticationType.GRANT_IMPLICIT, new GrantImplicitWorker(serviceLocator));
        this.authenticationWorkers.put(AuthenticationType.GRANT_CODE, new GrantCodeWorker(serviceLocator));
    }

    @Override
    public boolean isAuthenticated() {
        return getAuthenticationType() != null ? getWorker().isAuthenticated() : false;
    }

    @Override
    public String getHeader() {
        return getAuthenticationType() != null ? getWorker().getHeader() : null;
    }

    @Override
    public void authenticate() throws AuthenticationException {
        if (getAuthenticationType() == null){
            throw new AuthenticationException(AuthenticationException.WRONG_AUTHENTICATION_TYPE,
                    "You must specify an authentication type, see more info in java doc for " +
                            "AuthenticationService#setAuthenticationType(AuthenticationType)");
        }
        getWorker().authenticate();
    }

    @Override
    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    @Override
    public AuthenticationType getAuthenticationType() {
        return this.authenticationType;
    }

    @Override
    public void setAuthenticationProperty(String name, String value) {
        this.preferencesService.setString(name, value);
    }

    @Override
    public String getAuthenticationProperty(String name) {
        return this.preferencesService.getString(name);
    }

    @Override
    public String getOAuthUserEndPoint() {
        return getAuthenticationType() != null ? getWorker().getOAuthUrl() : null;
    }

    @Override
    public void parseOAuthResponse(String data) {
        if (data != null && getAuthenticationType() != null) {
            try {
                String charset = Charset.isSupported("UTF-8") ? "UTF-8" : Charset.defaultCharset().displayName();
                data = URLDecoder.decode(data, charset);
            } catch (UnsupportedEncodingException ex) {

            }
            getWorker().parseOAuthResponse(data);
        }
    }

    @Override
    public void logout() {
        if (getAuthenticationType() != null){
            getWorker().logout();
        }
    }

    private AuthenticationWorker getWorker(){
        return this.authenticationWorkers.get(getAuthenticationType());
    }
}
