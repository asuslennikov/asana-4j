package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.PagedList;
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

    /**
     * Returns list of all stories for a task.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param taskId           Globally unique identifier for the task.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the compact records for all stories on the task.
     * @api.link <a href="https://asana.com/developers/api-reference/stories#get-all">Get stories on object</a>
     * @see Story
     * @see PagedList
     * @see RequestModifier
     */
    PagedList<Story> getTaskStories(long taskId, RequestModifier... requestModifiers);

    /**
     * Adds a comment to a task. The comment will be authored by the currently authenticated user, and timestamped
     * when the server receives the request.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param taskId           Globally unique identifier for the task.
     * @param text             The plain text of the comment to add.
     * @param requestModifiers Additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the full record for the new story added to the task.
     * @api.link <a href="https://asana.com/developers/api-reference/stories#post-comment">Commenting on an object</a>
     * @see Story
     * @see RequestModifier
     */
    Story createTaskComment(long taskId, String text, RequestModifier... requestModifiers);
}
