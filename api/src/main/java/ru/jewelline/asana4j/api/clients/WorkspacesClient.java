package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.asana4j.api.entities.Workspace;
import ru.jewelline.request.http.modifiers.RequestModifier;

/**
 * Provides functionality for workspace management.
 */
public interface WorkspacesClient {
    /**
     * Returns the full workspace record for a single workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param workspaceId      globally unique identifier for the workspace or organization.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the full workspace record for a single workspace.
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#get">Get available workspaces</a>
     * @see RequestModifier
     * @see Workspace
     */
    Workspace getWorkspaceById(long workspaceId, RequestModifier... requestModifiers);

    /**
     * Returns the compact records for all workspaces visible to the authorized user.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the compact records for all workspaces visible to the authorized user.
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#get">Get available workspaces</a>
     * @see RequestModifier
     * @see Workspace
     */
    PagedList<Workspace> getWorkspaces(RequestModifier... requestModifiers);


    /**
     * Invites an user to specified workspace (or organisation).
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param workspaceId the workspace or organization to invite the user to.
     * @param userId      user unique identifier, see {@link User#getId()}
     * @return Returns the full user record for the invited user.
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #addUser(long, String)
     * @see #addCurrentUser(long)
     * @see User
     */
    User addUser(long workspaceId, long userId);

    /**
     * Invites an user to specified workspace (or organisation).
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param workspaceId the workspace or organization to invite the user to.
     * @param email       user email address, see {@link User#getEmail()}
     * @return Returns the full user record for the invited user.
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #addUser(long, long)
     * @see #addCurrentUser(long)
     * @see User
     */
    User addUser(long workspaceId, String email);

    /**
     * Invites an user to specified workspace (or organisation).
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param workspaceId the workspace or organization to invite the user to.
     * @return Returns the full user record for the invited user.
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #addUser(long, long)
     * @see #addUser(long, String)
     * @see User
     */
    User addCurrentUser(long workspaceId);

    /**
     * Removes the user from the specified workspace (or organisation).
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param workspaceId the workspace or organization to remove the user from.
     * @param userId      user unique identifier, see {@link User#getId()}
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #removeUser(long, String)
     * @see #removeCurrentUser(long)
     */
    void removeUser(long workspaceId, long userId);

    /**
     * Removes the user from the specified workspace (or organisation).
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param workspaceId the workspace or organization to remove the user from.
     * @param email       user email address, see {@link User#getEmail()}
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #removeUser(long, long)
     * @see #removeCurrentUser(long)
     */
    void removeUser(long workspaceId, String email);

    /**
     * Removes the currently authenticated user from the specified workspace (or organisation).
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param workspaceId the workspace or organization to remove the user from.
     * @api.link <a href="https://asana.com/developers/api-reference/workspaces#user-mgmt">User management</a>
     * @see #removeUser(long, long)
     * @see #removeUser(long, String)
     */
    void removeCurrentUser(long workspaceId);

    // TODO typeahead search
}
