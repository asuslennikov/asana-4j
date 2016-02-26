package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.entity.Attachment;
import ru.jewelline.request.http.modifiers.RequestModifier;

import java.io.InputStream;
import java.io.OutputStream;

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

    /**
     * Returns a specific existing attachment.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param attachmentId     Globally unique identifier for the attachment.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @return Returns the full record for a single attachment.
     * @api.link <a href="https://asana.com/developers/api-reference/attachments#get-single">Get single attachment</a>
     * @see Attachment
     * @see RequestModifier
     */
    Attachment getAttachmentById(long attachmentId, RequestModifier... requestModifiers);

    /**
     * Downloads the attachment using a result of the {@link Attachment#getDownloadUrl()} method as a source URL.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param attachmentId      Globally unique identifier for the attachment.
     * @param destinationStream A destination stream for that attachment.
     * @return <code>true</code> if attachment was downloaded, <code>false</code> if it can not
     * (for example if the {@link Attachment#getDownloadUrl()} is empty).
     * @see Attachment#getDownloadUrl()
     */
    boolean downloadAttachment(long attachmentId, OutputStream destinationStream);

    /**
     * Downloads the attachment preview using a result of the {@link Attachment#getViewUrl()} method as a source URL.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param attachmentId      Globally unique identifier for the attachment.
     * @param destinationStream A destination stream for that attachment.
     * @return <code>true</code> if attachment was downloaded, <code>false</code> if it can not
     * (for example if the {@link Attachment#getViewUrl()} is empty).
     * @see Attachment#getViewUrl()
     */
    boolean downloadAttachmentPreview(long attachmentId, OutputStream destinationStream);

    /**
     * This method uploads an attachment to a task and returns a compact record for the new attachment.
     * The 100MB size limit on attachments in Asana is enforced on this endpoint.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param taskId     Globally unique identifier for the task.
     * @param name       A name for attachment.
     * @param attachment Payload which should be added as an attachment for the task.
     * @return Returns a compact record for a single attachment.
     * @api.link <a href="https://asana.com/developers/api-reference/attachments#upload">Upload an attachment</a>
     * @see Attachment
     */
    Attachment uploadAttachment(long taskId, String name, InputStream attachment);
}
