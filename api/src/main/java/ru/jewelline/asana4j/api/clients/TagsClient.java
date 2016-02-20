package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.asana4j.api.entities.Tag;
import ru.jewelline.asana4j.api.entities.Task;
import ru.jewelline.request.http.modifiers.RequestModifier;

/**
 * Provides functionality for tag management.
 */
public interface TagsClient {

    /**
     * Returns the complete tag record for a single tag.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param tagId            globally unique identifier for the tag.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the complete tag record for a single tag.
     * @api.link <a href="https://asana.com/developers/api-reference/tags#get-single">Get a single tag</a>
     * @see RequestModifier
     */
    Tag getTagById(long tagId, RequestModifier... requestModifiers);

    /**
     * Starts a new tag creation process. Every tag is required to be created in a specific workspace or organization,
     * and this cannot be changed once set.
     *
     * @param workspaceId workspace for which we want to create a tag.
     * @return A builder which allows for user to specify parameters for the new tag and complete the creation process
     * (workspace id is already set, see {@link Tag.TagCreator#setWorkspace(long)}).
     * @api.link <a href="https://asana.com/developers/api-reference/tags#create">Create a tag</a>
     * @see Tag
     * @see Tag.TagCreator
     */
    Tag.TagCreator createTag(long workspaceId);

    /**
     * Returns the compact tag records for some filtered set of tags.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param filter           a filter with specified search criterias.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the compact tag records for some filtered set of tags
     * @api.link <a href="https://asana.com/developers/api-reference/tags#query">Query for tags</a>
     */
    PagedList<Tag> getTags(TagFilter filter, RequestModifier... requestModifiers);

    /**
     * Returns the compact task records for all tasks with the given tag. Tasks can have more than one tag at a time.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param tagId            globally unique identifier for the tag.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the compact task records for all tasks with the given tag.
     * @api.link <a href="https://asana.com/developers/api-reference/tags#get-tasks">Get tasks with tag</a>
     */
    PagedList<Task> getTasksForTag(long tagId, RequestModifier... requestModifiers);
}
