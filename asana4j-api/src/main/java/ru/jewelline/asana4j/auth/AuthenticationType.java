package ru.jewelline.asana4j.auth;

/**
 * Authentication types which can be used for authenticate a request.
 * More information about authentication types which are available you can find on
 * <a href="https://asana.com/developers/documentation/getting-started/authentication" title="Documentation">official page</a>.
 */
public enum AuthenticationType {
    /**
     * To authenticate a client, we need just an API key.
     * <ul> List of required authentication parameters:
     *      <li>{@link AuthenticationProperties#API_KEY}</li>
     * </ul>
     */
    BASIC,

    /**
     * <ul> List of required authentication parameters:
     *      <li>{@link AuthenticationProperties#CLIENT_ID}</li>
     *      <li>{@link AuthenticationProperties#AUTHORIZATION_ENDPOINT_REDIRECT_URL}</li>
     * </ul>
     */
    GRANT_IMPLICIT,

    /**
     * To authenticate a client, we need somehow to obtain an <code>code</code> value
     * <ul> List of required authentication parameters:
     *      <li>{@link AuthenticationProperties#CLIENT_ID}</li>
     *      <li>{@link AuthenticationProperties#CLIENT_SECRET}</li>
     *      <li>{@link AuthenticationProperties#AUTHORIZATION_ENDPOINT_REDIRECT_URL}</li>
     *      <li>{@link AuthenticationProperties#ACCESS_CODE}</li>
     * </ul>
     */
    GRANT_CODE,
    ;
}
