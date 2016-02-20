package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.request.http.modifiers.RequestModifier;

/**
 * Provides functionality for user management.
 */
public interface UsersClient {
    /**
     * Returns the currently authenticated user.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the full user record for the currently authenticated user.
     * @api.link <a href="https://asana.com/developers/api-reference/users#get-single">Get a single user</a>
     * @see User
     * @see RequestModifier
     */
    User getCurrentUser(RequestModifier... requestModifiers);

    /**
     * Returns a specific existing user.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param userId           globally unique identifier for the user.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the full user record for the single user with the provided ID.
     * @api.link <a href="https://asana.com/developers/api-reference/users#get-single">Get a single user</a>
     * @see User
     * @see RequestModifier
     */
    User getUserById(long userId, RequestModifier... requestModifiers);

    /**
     * Returns all users in all workspaces and organizations accessible to the authenticated user.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return RReturns the user records for all users in all workspaces and organizations accessible to the
     * authenticated user.
     * @api.link <a href="https://asana.com/developers/api-reference/users#get-all">Get all users</a>
     * @see User
     * @see RequestModifier
     */
    PagedList<User> getUsers(RequestModifier... requestModifiers);

    /**
     * Returns all users for the given workspace (organization).
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param workspaceId      the workspace in which to get users.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the user records for all users in the specified workspace or organization. Results are sorted
     * alphabetically by user names.
     * @api.link <a href="https://asana.com/developers/api-reference/users#get-all">Get all users</a>
     * @see User
     * @see RequestModifier
     */
    PagedList<User> getWorkspaceUsers(long workspaceId, RequestModifier... requestModifiers);
}
