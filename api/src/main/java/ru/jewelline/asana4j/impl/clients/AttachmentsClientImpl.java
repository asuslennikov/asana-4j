package ru.jewelline.asana4j.impl.clients;

import ru.jewelline.asana.core.utils.MultipartFormEntity;
import ru.jewelline.asana4j.api.clients.AttachmentsClient;
import ru.jewelline.asana4j.api.entities.Attachment;
import ru.jewelline.asana4j.api.models.AttachmentModel;
import ru.jewelline.asana4j.api.modifiers.QueryFieldsModifier;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.entity.EntityDeserializer;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

import java.io.InputStream;
import java.io.OutputStream;

public class AttachmentsClientImpl extends ApiClientImpl implements AttachmentsClient {

    public AttachmentsClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<AttachmentModel> getAttachmentDeserializer() {
        return getEntityContext().getDeserializer(AttachmentModel.class);
    }

    @Override
    public PagedList<Attachment> getTaskAttachments(long taskId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("tasks/" + taskId + "/attachments")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getAttachmentDeserializer());
    }

    @Override
    public Attachment getAttachmentById(long attachmentId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("attachments/" + String.valueOf(attachmentId))
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getAttachmentDeserializer());
    }

    @Override
    public boolean downloadAttachmentPreview(long attachmentId, OutputStream destinationStream) {
        Attachment attachment = getAttachmentById(attachmentId, new QueryFieldsModifier("download_url", "view_url"));
        return attachment.downloadPreview(destinationStream);
    }

    @Override
    public boolean downloadAttachment(long attachmentId, OutputStream destinationStream) {
        Attachment attachment = getAttachmentById(attachmentId, new QueryFieldsModifier("download_url", "view_url"));
        return attachment.download(destinationStream);
    }

    @Override
    public Attachment uploadAttachment(long taskId, String name, InputStream attachment) {
        MultipartFormEntity entity = new MultipartFormEntity(name, attachment);
        return apiRequest()
                .path("tasks/" + taskId + "/attachments")
                .setHeader("Content-Type", "multipart/form-data; boundary=" + entity.getBoundary())
                .setEntity(entity)
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getAttachmentDeserializer());
    }
}
