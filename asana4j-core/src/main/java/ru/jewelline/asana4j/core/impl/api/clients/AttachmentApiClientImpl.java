package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.AttachmentApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Attachment;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.AttachmentImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

public class AttachmentApiClientImpl extends ApiClientImpl implements AttachmentApiClient {

    public AttachmentApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
    }

    @Override
    public PagedList<Attachment> getTaskAttachments(long taskId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("tasks/" + taskId + "/attachments")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getEntityContext().getDeserializer(AttachmentImpl.class));
    }
}
