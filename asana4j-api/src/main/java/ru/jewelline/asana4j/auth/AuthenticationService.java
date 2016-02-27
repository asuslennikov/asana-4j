package ru.jewelline.asana4j.auth;

import ru.jewelline.asana4j.utils.PropertiesStore;

/**
 * Stateful service which provides methods for client authentication. <br />
 * You can find detailed description for Asana authentication process
 * <a href="https://asana.com/developers/documentation/getting-started/authentication">here</a>.
 */
public interface AuthenticationService {

    /**
     * This method provides info about authentication status for the client. <br />
     * It is possible that this method returns <code>true</code>, but you can receive the 'expired token' response for your request.
     * In this case your further steps depends on the selected authentication type:
     * <ul>
     * <li>This case is impossible for {@link AuthenticationType#BASIC} type</li>
     * <li>In case of {@link AuthenticationType#GRANT_CODE} just call the {@link #authenticate()} method.</li>
     * <li>In case of {@link AuthenticationType#GRANT_IMPLICIT} you need to forward the client's user to user
     * authentication endpoint (see {@link #getOAuthUserEndPoint()})</li>
     * </ul>.
     *
     * @return <code>true</code> if the client is authenticated in <a href="https://asana.com" title="Asana application">Asana</a>.
     */
    boolean isAuthenticated();

    /**
     * This method provides a {@link String} value for 'Authorization' header in your request. <br />
     * <ul>Examples:
     * <li>Basic RmNaMjMuTTR4c010WG1US21EQTRzc0xDa0VuWWk6</li>
     * <li>Bearer 0/d3afa84f920bc329e6b0d047c9a7bc9d</li>
     * </ul>
     * The exact authentication type depends on {@link #getAuthenticationType()} value.
     *
     * @return <code>null</code> {@link #isAuthenticated()} returns <code>false</code>
     * and <code>not-null</code> instance otherwise
     */
    String getHeader();

    /**
     * This method tries to authenticate the client in <a href="https://asana.com" title="Asana application">Asana</a>
     * based on pre-filled properties, see {@link #setAuthenticationProperty(AuthenticationProperty, String)}
     *
     * @throws AuthenticationException if authentication was failed
     */
    void authenticate() throws AuthenticationException;


    /**
     * This method changes current authentication type.
     *
     * @param authenticationType desired authentication type
     */
    void setAuthenticationType(AuthenticationType authenticationType);

    /**
     * Provides information about chosen authentication type
     *
     * @return current authentication type
     */
    AuthenticationType getAuthenticationType();

    /**
     * Allows to set authentication properties for specific authentication type (see {@link #getAuthenticationType()}.
     *
     * @param property authentication property
     * @param value    authentication property value
     */
    void setAuthenticationProperty(AuthenticationProperty property, String value);

    /**
     * This method provides access by property name for authorization parameters,
     * which were set by {@link #setAuthenticationProperty(AuthenticationProperty, String)}
     *
     * @param property authentication property
     * @return current value for the authentication property which was specified by name parameter.
     * <code>null</code> if property is not defined in current context
     */
    String getAuthenticationProperty(AuthenticationProperty property);

    /**
     * Several authentication types require a client's user to enter his credentials. According to OAuth2 specification
     * user should be redirected to the specific page. If user grants permissions for the client on this page, he will be redirected
     * back to our client (page which was specified via
     * {@link AuthenticationProperty#AUTHORIZATION_ENDPOINT_REDIRECT_URL} property).
     *
     * @return page url on which user should be redirected. Can be <code>null</code> (for example, if chosen authentication type
     * {@link #getAuthenticationType()} doesn't require that)
     */
    String getOAuthUserEndPoint();

    /**
     * It parses response from OAuth user endpoint based on current authentication type (see {@link #getAuthenticationType()})
     * and after that fills authentication properties. If OAuth response is incorrect, nothing happens.
     *
     * @param data response from OAuth user endpoint (full redirect address)
     */
    void parseOAuthResponse(String data);

    /**
     * Forgot current credentials
     */
    void logout();

    /**
     * Stores all authentications properties with their values.
     *
     * @param propertiesStore store implementation.
     */
    void save(PropertiesStore propertiesStore);

    /**
     * Reads all authentications properties with their values.
     *
     * @param propertiesStore store implementation.
     */
    void load(PropertiesStore propertiesStore);
}
