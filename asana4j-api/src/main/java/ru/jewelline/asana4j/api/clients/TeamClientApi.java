package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Team;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;

public interface TeamClientApi {

    /**
     * Returns a specific existing team.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param teamId           Globally unique identifier for the team.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the full record for a single team.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#get">Get teams in organization</a>
     * @see Team
     * @see RequestModifier
     */
    Team getTeamById(long teamId, RequestModifier... requestModifiers);

    /**
     * Returns the compact records for all teams in the organization visible to the authorized user.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param organizationId   Globally unique identifier for the workspace or organization.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the compact records for all teams in the organization visible to the authorized user.
     * @throws ApiException if you provided an id of regular workspace instead of valid organisation.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#get">Get teams in organization</a>
     * @see Team
     * @see Workspace#isOrganisation()
     * @see RequestModifier
     */
    PagedList<Team> getTeamsInOrganisation(long organizationId, RequestModifier... requestModifiers);

    /**
     * Returns the compact records for all users that are members of the team.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param teamId           Globally unique identifier for the team.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the compact records for all users that are members of the team.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see User
     * @see RequestModifier
     */
    PagedList<User> getTeamUsers(long teamId, RequestModifier... requestModifiers);

    /**
     * Adds an user to the team. The user making this call must be a member of the team in order to add others. The user
     * to add must exist in the same organization as the team in order to be added.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param teamId Globally unique identifier for the team.
     * @param userId user unique identifier, see {@link User#getId()}
     * @return Returns the full user record for the added user.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #addUserToTeam(long, String)
     * @see #addCurrentUserToTeam(long)
     * @see User
     */
    User addUserToTeam(long teamId, long userId);

    /**
     * Adds an user to the team. The user making this call must be a member of the team in order to add others. The user
     * to add must exist in the same organization as the team in order to be added.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param teamId Globally unique identifier for the team.
     * @param email  user email address, see {@link User#getEmail()}
     * @return Returns the full user record for the added user.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #addUserToTeam(long, long)
     * @see #addCurrentUserToTeam(long)
     * @see User
     */
    User addUserToTeam(long teamId, String email);

    /**
     * Adds an user to the team. The user making this call must be a member of the team in order to add others. The user
     * to add must exist in the same organization as the team in order to be added.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param teamId Globally unique identifier for the team.
     * @return Returns the full user record for the added user.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #addUserToTeam(long, long)
     * @see #addUserToTeam(long, String)
     * @see User
     */
    User addCurrentUserToTeam(long teamId);

    /**
     * Removes the user from the specified team. The user making this call must be an admin in the workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param teamId Globally unique identifier for the team.
     * @param userId user unique identifier, see {@link User#getId()}
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #removeUserFromTeam(long, String)
     * @see #removeCurrentUserFromTeam(long)
     */
    void removeUserFromTeam(long teamId, long userId);

    /**
     * Removes the user from the specified team. The user making this call must be an admin in the workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param teamId Globally unique identifier for the team.
     * @param email  user email address, see {@link User#getEmail()}
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #removeUserFromTeam(long, long)
     * @see #removeCurrentUserFromTeam(long)
     */
    void removeUserFromTeam(long teamId, String email);

    /**
     * Removes the user from the specified team. The user making this call must be an admin in the workspace.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param teamId Globally unique identifier for the team.
     * @api.link <a href="https://asana.com/developers/api-reference/teams#users">Get team members</a>
     * @see #removeUserFromTeam(long, long)
     * @see #removeUserFromTeam(long, String)
     */
    void removeCurrentUserFromTeam(long teamId);
}
