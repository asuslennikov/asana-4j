package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.AttachmentApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.QueryFieldsModifier;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Attachment;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.AttachmentImpl;
import ru.jewelline.asana4j.core.impl.api.entity.io.MultipartFormEntity;
import ru.jewelline.asana4j.http.HttpMethod;

import java.io.InputStream;
import java.io.OutputStream;

public class AttachmentApiClientImpl extends ApiClientImpl implements AttachmentApiClient {

    public AttachmentApiClientImpl(RequestFactory requestFactory) {
        super(requestFactory);
    }

    private EntityDeserializer<AttachmentImpl> getAttachmentDeserializer() {
        return getEntityContext().getDeserializer(AttachmentImpl.class);
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
