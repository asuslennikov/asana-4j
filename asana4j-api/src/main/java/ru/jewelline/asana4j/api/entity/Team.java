package ru.jewelline.asana4j.api.entity;

import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.modifiers.RequestModifier;

/**
 * A team is used to group related projects and people together within an organization. Each project in an
 * organization is associated with a team.
 *
 * @api.link <a href="https://asana.com/developers/api-reference/teams">Teams API page</a>
 * @see ru.jewelline.asana4j.api.entity.HasId
 * @see ru.jewelline.asana4j.api.entity.HasName
 */
public interface Team extends HasId, HasName {

    /**
     * Returns the compact records for all users that are members of the team.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the compact records for all users that are members of the team.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see User
     * @see RequestModifier
     */
    PagedList<User> getUsers(RequestModifier... requestModifiers);

    /**
     * Adds an user to the team. The user making this call must be a member of the team in order to add others. The user
     * to add must exist in the same organization as the team in order to be added.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param userId user unique identifier, see {@link User#getId()}
     * @return Returns the full user record for the added user.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #addUser(String)
     * @see #addCurrentUser()
     * @see User
     */
    User addUser(long userId);

    /**
     * Adds an user to the team. The user making this call must be a member of the team in order to add others. The user
     * to add must exist in the same organization as the team in order to be added.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param email user email address, see {@link User#getEmail()}
     * @return Returns the full user record for the added user.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #addUser(long)
     * @see #addCurrentUser()
     * @see User
     */
    User addUser(String email);

    /**
     * Adds an user to the team. The user making this call must be a member of the team in order to add others. The user
     * to add must exist in the same organization as the team in order to be added.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @return Returns the full user record for the added user.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #addUser(long)
     * @see #addUser(String)
     * @see User
     */
    User addCurrentUser();

    /**
     * Removes the user from the specified team. The user making this call must be an admin in the workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param userId user unique identifier, see {@link User#getId()}
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #removeUser(long)
     * @see #removeCurrentUser()
     */
    void removeUser(long userId);

    /**
     * Removes the user from the specified team. The user making this call must be an admin in the workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param email user email address, see {@link User#getEmail()}
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #removeUser(long)
     * @see #removeCurrentUser()
     */
    void removeUser(String email);

    /**
     * Removes the user from the specified team. The user making this call must be an admin in the workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #removeUser(long)
     * @see #removeUser(String)
     */
    void removeCurrentUser();

    /**
     * Creates a project shared with the given team.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param name a display name of project
     * @return the full record of the newly created project.
     * @api.link <a href="https://asana.com/developers/api-reference/projects#create">Create a project</a>
     * @see Project
     * @see Team#createProject(String)
     */
    Project createProject(String name);
}
