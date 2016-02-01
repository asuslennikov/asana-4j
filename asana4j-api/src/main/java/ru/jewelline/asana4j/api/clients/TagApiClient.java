package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Tag;

public interface TagApiClient {

    /**
     * Returns the complete tag record for a single tag.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param tagId     Globally unique identifier for the attachment.
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
     * @return A builder which allows for user to specify parameters for the new tag and complete the creation process
     * (workspace id is already set, see {@link ru.jewelline.asana4j.api.entity.Tag.TagCreator#setWorkspace(long)}).
     * @api.link @api.link <a href="https://asana.com/developers/api-reference/tags#create">Create a tag</a>
     * @see Tag
     * @see ru.jewelline.asana4j.api.entity.Tag.TagCreator
     */
    Tag.TagCreator createTag(long workspaceId);
}
