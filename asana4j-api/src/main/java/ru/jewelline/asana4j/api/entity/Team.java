package ru.jewelline.asana4j.api.entity;

/**
 * A team is used to group related projects and people together within an organization. Each project in an
 * organization is associated with a team.
 *
 * @api.link <a href="https://asana.com/developers/api-reference/teams">Teams API page</a>
 * @see ru.jewelline.asana4j.api.entity.HasId
 * @see ru.jewelline.asana4j.api.entity.HasName
 */
public interface Team extends HasId, HasName {
    // Team has just the id and name
}
