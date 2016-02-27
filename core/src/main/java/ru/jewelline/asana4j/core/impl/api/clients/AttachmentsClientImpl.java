package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.AttachmentsClient;
import ru.jewelline.asana4j.api.clients.modifiers.QueryFieldsModifier;
import ru.jewelline.asana4j.api.entity.Attachment;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.AttachmentImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityResponseReceiver;
import ru.jewelline.asana4j.core.impl.api.entity.io.MultipartFormEntity;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

import java.io.InputStream;
import java.io.OutputStream;

public class AttachmentsClientImpl extends ApiClientImpl implements AttachmentsClient {

    public AttachmentsClientImpl(HttpRequestFactory httpRequestFactory, ApiEntityContext entityContext) {
        super(httpRequestFactory, entityContext);
    }

    private EntityDeserializer<AttachmentImpl> getAttachmentDeserializer() {
        return getEntityContext().getDeserializer(AttachmentImpl.class);
    }

    @Override
    public PagedList<Attachment> getTaskAttachments(long taskId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("tasks/" + taskId + "/attachments")
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
                .asApiCollection(getAttachmentDeserializer());
    }

    @Override
    public Attachment getAttachmentById(long attachmentId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("attachments/" + String.valueOf(attachmentId))
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
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
        return newRequest()
                .setUrl("tasks/" + taskId + "/attachments")
                .setHeader("Content-Type", "multipart/form-data; boundary=" + entity.getBoundary())
                .setEntity(entity)
                .buildAs(HttpMethod.POST)
                .execute(new ApiEntityResponseReceiver())
                .asApiObject(getAttachmentDeserializer());
    }
}
