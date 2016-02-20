package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.asana4j.api.entities.Tag;
import ru.jewelline.asana4j.api.entities.Task;
import ru.jewelline.request.http.modifiers.RequestModifier;

public interface TaskApiClient {

    Task getTaskById(long taskId, RequestModifier... requestModifiers);

    PagedList<Task> getTasksForProject(long projectId, RequestModifier... requestModifiers);

    void deleteTask(long taskId);

    /**
     * Returns a compact representation of all of the tags the task has.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param taskId           The task to get tags on.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns a compact representation of all of the tags the task has.
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#tags">Tags on tasks</a>
     * @see Tag
     * @see RequestModifier
     */
    PagedList<Tag> getTaskTags(long taskId, RequestModifier... requestModifiers);

    /**
     * Adds a tag to a task.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param taskId The task to add a tag to.
     * @param tagId  The tag to add to the task.
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#tags">Tags on tasks</a>
     */
    void addTag(long taskId, long tagId);

    /**
     * Removes a tag from the task.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param taskId The task to remove a tag from.
     * @param tagId  The tag to remove from the task.
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#tags">Tags on tasks</a>
     */
    void removeTag(long taskId, long tagId);
}
