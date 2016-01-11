package ru.jewelline.asana4j.core.impl.auth;

import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.auth.AuthenticationType;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.utils.Base64;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.StringUtils;
import ru.jewelline.asana4j.utils.URLCreator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.EnumMap;
import java.util.Map;

/**
 * This class is not thread safe.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Map<AuthenticationType, AuthenticationWorker> authenticationWorkers;
    private final PreferencesService preferencesService;

    private volatile AuthenticationType authenticationType;

    public AuthenticationServiceImpl(PreferencesService preferencesService, HttpClient httpClient, URLCreator urlCreator, Base64 base64) {
        this.preferencesService = preferencesService;
        this.authenticationWorkers = new EnumMap<>(AuthenticationType.class);
        // TODO Don't pass 'this' out of a constructor
        this.authenticationWorkers.put(AuthenticationType.BASIC, new BasicAuthenticationWorker(this, base64));
        this.authenticationWorkers.put(AuthenticationType.GRANT_IMPLICIT, new GrantImplicitWorker(this, urlCreator));
        this.authenticationWorkers.put(AuthenticationType.GRANT_CODE, new GrantCodeWorker(this, httpClient, urlCreator));
    }

    @Override
    public boolean isAuthenticated() {
        return getAuthenticationType() != null && getWorker().isAuthenticated();
    }

    @Override
    public String getHeader() {
        return getAuthenticationType() != null ? getWorker().getHeader() : null;
    }

    @Override
    public void authenticate() throws AuthenticationException {
        if (getAuthenticationType() == null) {
            throw new AuthenticationException(AuthenticationException.WRONG_AUTHENTICATION_TYPE,
                    "You must specify an authentication type, see more info in java doc for " +
                            "AuthenticationService#setAuthenticationType(AuthenticationType)");
        }
        getWorker().authenticate();
    }

    @Override
    public AuthenticationType getAuthenticationType() {
        return this.authenticationType;
    }

    @Override
    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
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
                data = URLDecoder.decode(data, StringUtils.getCharset().displayName());
            } catch (UnsupportedEncodingException ex) {
                /* can happen only if charset doesn't exist for the given name,
                   but we use the charset instance, so it always exists */
            }
            getWorker().parseOAuthResponse(data);
        }
    }

    @Override
    public void logout() {
        if (getAuthenticationType() != null) {
            getWorker().logout();
        }
    }

    private AuthenticationWorker getWorker() {
        return this.authenticationWorkers.get(getAuthenticationType());
    }
}
