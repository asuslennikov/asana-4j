package ru.jewelline.asana4j.api.entity;

import java.util.List;

/**
 * A tag is a label that can be attached to any task in Asana. It exists in a single workspace or organization.
 * <p>
 * Tags have some metadata associated with them, but it is possible that we will simplify them in the future so it is
 * not encouraged to rely too heavily on it. Unlike projects, tags do not provide any ordering on the tasks they are
 * associated with.
 *
 * @api.link <a href="https://asana.com/developers/api-reference/tags">Tags</a>
 * @see HasId
 * @see HasName
 */
public interface Tag extends HasId, HasName {
    /**
     * @return The time at which this tag was created.
     * @api.field <code>created_at</code>
     * @api.access Read-only
     */
    String getCreatedAt();

    /**
     * @return  Array of users following this tag.
     * @api.field <code>followers</code>
     * @api.access Read-write
     * @see User
     */
    List<User> getFollowers();

    /**
     * @return Color of the project. Must be one of the values from the * {@link ProjectColor} enum.
     * @api.field <code>color</code>
     * @api.access Read-write
     * @see Project.ProjectUpdater#setColor(ProjectColor)
     * @see ProjectColor
     */
    ProjectColor getColor();

    /**
     * @return More detailed, free-form textual information associated with the tag.
     * @api.field <code>notes</code>
     * @api.access Read-write
     * @see Task.TaskBuilder#setNotes(String)
     */
    String getNotes();

    /**
     * @return The workspace or organization this tag is associated with. Once created, tags cannot be moved to a
     * different workspace. This attribute can only be specified at creation time.
     * @api.field <code>workspace</code>
     * @api.access Create-only
     * @see Task.TaskCreator#setWorkspace(long)
     * @see Workspace
     */
    Workspace getWorkspace();
}
