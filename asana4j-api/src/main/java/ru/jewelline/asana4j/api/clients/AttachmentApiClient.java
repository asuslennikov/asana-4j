package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Attachment;

public interface AttachmentApiClient {

    /**
     * Returns list of all attachments for that task.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param taskId           Globally unique identifier for the task.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the compact records for all attachments on the task.
     * @api.link <a href="https://asana.com/developers/api-reference/attachments#get-all-task">Get all attachments</a>
     * @see Attachment
     * @see PagedList
     * @see RequestModifier
     */
    PagedList<Attachment> getTaskAttachments(long taskId, RequestModifier... requestModifiers);

}
