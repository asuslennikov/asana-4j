package ru.jewelline.asana.auth;

/**
 * Authentication types which can be used for authenticate a request.
 * More information about authentication types which are available you can find on
 * <a href="https://asana.com/developers/documentation/getting-started/authentication" title="Documentation">official page</a>.
 */
public enum AuthenticationType {
    /**
     * To authenticate a client, we need just an API key.
     * <ul> List of required authentication parameters:
     *      <li>{@link AuthenticationProperty#API_KEY}</li>
     * </ul>
     */
    API_KEY,

    /**
     * <ul> List of required authentication parameters:
     *      <li>{@link AuthenticationProperty#CLIENT_ID}</li>
     *      <li>{@link AuthenticationProperty#AUTHORIZATION_ENDPOINT_REDIRECT_URL}</li>
     * </ul>
     */
    GRANT_IMPLICIT,

    /**
     * To authenticate a client, we need somehow to obtain an <code>code</code> value
     * <ul> List of required authentication parameters:
     *      <li>{@link AuthenticationProperty#CLIENT_ID}</li>
     *      <li>{@link AuthenticationProperty#CLIENT_SECRET}</li>
     *      <li>{@link AuthenticationProperty#AUTHORIZATION_ENDPOINT_REDIRECT_URL}</li>
     *      <li>{@link AuthenticationProperty#ACCESS_CODE}</li>
     * </ul>
     */
    GRANT_CODE,

    /**
     * Personal Access Tokens are a useful mechanism for accessing the API in scenarios where OAuth would be considered
     * overkill, such as access from the command line and personal scripts or applications. A user can create many, but
     * not unlimited, personal access tokens. When creating a token you must give it a description to help you remember
     * what you created the token for.
     * <ul> List of required authentication parameters:
     *   <li>{@link AuthenticationProperty#ACCESS_TOKEN}</li>
     * </ul>
     *
     * @api.link <a href="https://asana.com/developers/documentation/getting-started/auth#personal-access-token">Personal Access Token</a>
     */
    PERSONAL_ACCESS_TOKEN,
    ;
}
