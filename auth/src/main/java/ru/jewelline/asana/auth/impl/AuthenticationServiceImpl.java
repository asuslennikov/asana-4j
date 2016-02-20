package ru.jewelline.asana.auth.impl;

import ru.jewelline.asana.auth.AuthenticationException;
import ru.jewelline.asana.auth.AuthenticationProperty;
import ru.jewelline.asana.auth.AuthenticationService;
import ru.jewelline.asana.auth.AuthenticationType;
import ru.jewelline.asana.auth.PropertiesStore;
import ru.jewelline.asana.core.EntityContext;
import ru.jewelline.asana.core.utils.Base64;
import ru.jewelline.request.http.HttpRequestFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.EnumMap;
import java.util.Map;

/**
 * This class is not thread safe.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private final HttpRequestFactory httpRequestFactory;
    private final Map<AuthenticationType, AuthenticationWorker> authenticationWorkers;
    private final Map<AuthenticationProperty, String> properties;

    private volatile AuthenticationType authenticationType;

    public AuthenticationServiceImpl(HttpRequestFactory httpRequestFactory, EntityContext entityContext, Base64 base64) {
        this.httpRequestFactory = httpRequestFactory;
        this.authenticationWorkers = new EnumMap<>(AuthenticationType.class);
        this.properties = new EnumMap<>(AuthenticationProperty.class);
        // TODO Don't pass 'this' out of a constructor
        this.authenticationWorkers.put(AuthenticationType.API_KEY, new ApiKeyWorker(this, base64));
        this.authenticationWorkers.put(AuthenticationType.GRANT_IMPLICIT, new GrantImplicitWorker(this, httpRequestFactory));
        this.authenticationWorkers.put(AuthenticationType.GRANT_CODE, new GrantCodeWorker(this, httpRequestFactory, entityContext));
        this.authenticationWorkers.put(AuthenticationType.PERSONAL_ACCESS_TOKEN, new PersonalAccessTokenWorker(this));
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
    public void setAuthenticationProperty(AuthenticationProperty property, String value) {
        if (property != null) {
            if (value == null) {
                this.properties.remove(property);
            } else {
                this.properties.put(property, value);
            }
        }
    }

    @Override
    public String getAuthenticationProperty(AuthenticationProperty property) {
        return this.properties.get(property);
    }

    @Override
    public String getOAuthUserEndPoint() {
        return getAuthenticationType() != null ? getWorker().getOAuthUrl() : null;
    }

    @Override
    public void parseOAuthResponse(String data) {
        if (data != null && getAuthenticationType() != null) {
            try {
                data = URLDecoder.decode(data, this.httpRequestFactory.getHttpConfiguration().getUrlCharset().displayName());
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

    @Override
    public void save(PropertiesStore propertiesStore) {
        if (propertiesStore != null) {
            for (AuthenticationProperty property : AuthenticationProperty.values()) {
                propertiesStore.setString(property.getPropertyName(), getAuthenticationProperty(property));
            }
        }
    }

    @Override
    public void load(PropertiesStore propertiesStore) {
        if (propertiesStore != null) {
            for (AuthenticationProperty property : AuthenticationProperty.values()) {
                setAuthenticationProperty(property, propertiesStore.getString(property.getPropertyName()));
            }
        }
    }

    private AuthenticationWorker getWorker() {
        return this.authenticationWorkers.get(getAuthenticationType());
    }
}
