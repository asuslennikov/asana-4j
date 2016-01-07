package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Story;

public interface StoryApiClient {

    /**
     * Returns a specific existing story.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param storyId          Globally unique identifier for the story.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the full record for a single story.
     * @api.link <a href="https://asana.com/developers/api-reference/stories#get-single">Get a single story</a>
     * @see Story
     * @see RequestModifier
     */
    Story getStoryById(long storyId, RequestModifier... requestModifiers);
}
