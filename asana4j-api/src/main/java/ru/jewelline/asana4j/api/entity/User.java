package ru.jewelline.asana4j.api.entity;

import java.util.List;

/**
 * A user object represents an account in Asana that can be given access to various workspaces, projects, and tasks.
 * <p>
 * Like other objects in the system, users are referred to by numerical IDs. However, the special string identifier
 * <code>me</code> can be used anywhere a user ID is accepted, to refer to the current authenticated user.
 *
 * @api.link <a href="https://asana.com/developers/api-reference/users">Users API page</a>
 * @see ru.jewelline.asana4j.api.entity.HasId
 * @see ru.jewelline.asana4j.api.entity.HasName
 */
public interface User extends HasId, HasName {

    /**
     * @return The user’s email address
     * @api.field <code>email</code>
     * @api.access Read-only
     */
    String getEmail();

    /**
     * @return A map of the user's profile photo in various sizes, or null if no photo is set. <p>
     * Sizes provided are 21, 27, 36, 60, and 128. Images are in PNG format.
     * @api.field <code>photo</code>
     * @api.access Read-only
     */
    //TODO change type for the url, it should be map with size-path pairs
    String getPhotoUrl();

    /**
     * @return Workspaces and organizations this user may access.<p>
     * The API will only return workspaces and organizations that are also available for the authenticated user.
     * @api.field <code>workspaces</code>
     * @api.access Read-only
     */
    List<Workspace> getWorkspaces();
}
