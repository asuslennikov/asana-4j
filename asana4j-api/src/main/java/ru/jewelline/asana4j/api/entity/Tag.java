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
     * @return Array of users following this tag.
     * @api.field <code>followers</code>
     * @api.access Read-write
     * @see User
     */
    List<User> getFollowers();

    /**
     * @return Color of the project. Must be one of the values from the {@link ProjectColor} enum.
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

    /**
     * Starts an update process for the tag.
     *
     * @return A specific builder which allows to update required fields and save these changes (or discard).
     * @throws IllegalStateException if another update process is in progress
     * @api.link <a href="https://asana.com/developers/api-reference/tags#update">Update a tag</a>
     */
    TagUpdater startUpdate();

    /**
     * A base builder class which allows for user to set values for tag fields during
     * the edit or create operation.
     */
    interface TagBuilder<T extends TagBuilder> {

        /**
         * Sets a name for the tag.
         *
         * @param name Name of the tag. This is generally a short sentence fragment that fits on a line in the UI
         *             for maximum readability.
         * @return The builder.
         * @see Tag#getName()
         */
        T setName(String name);

        /**
         * Sets a description for the tag.
         *
         * @param notes More detailed, free-form textual information associated with the tag.
         * @return The builder.
         * @see Tag#getNotes()
         */
        T setNotes(String notes);

        /**
         * Sets a color for the tag
         *
         * @param color Color of the project.
         * @return The builder.
         * @see Tag#getColor()
         */
        T setColor(ProjectColor color);
    }

    /**
     * A builder class which allows for user to specify tag fields. Only touched fields will be
     * saved.
     *
     * @see Workspace#createTag()
     * @see Project#createTask()
     * @see Task#createSubTask()
     */
    interface TagCreator extends TagBuilder<TagCreator> {

        /**
         * Every tag is required to be created in a specific workspace or organization, and this cannot be changed
         * once set.
         *
         * @param workspaceId The workspace or organization to create the tag in.
         * @return The builder.
         * @see Task#getWorkspace()
         * @see Workspace
         */
        TagCreator setWorkspace(long workspaceId);

        /**
         * Creates a new tag in a workspace or organization.
         * <p><i>Triggers HTTP communication with server</i></p>
         *
         * @return The full record of the newly created tag.
         * @api.link <a href="https://asana.com/developers/api-reference/tags#create">Create a tag</a>
         */
        Tag create();
    }

    /**
     * A builder class which allows for user to set new values for tag fields. Only touched fields will be
     * saved.
     */
    interface TagUpdater extends TagBuilder<TagUpdater> {
        /**
         * Dismisses changes for the tag.
         *
         * @return The tag with original fields.
         */
        Tag abandon();

        /**
         * Applies changes for the tag.
         * <p><i>Triggers HTTP communication with server</i></p>
         *
         * @return The complete updated tag record.
         * @api.link <a href="https://asana.com/developers/api-reference/tags#update">Update a tag</a>
         */
        Tag update();
    }
}
