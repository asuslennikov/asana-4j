package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.WorkspacesClient;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityResponseReceiver;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class WorkspacesClientImpl extends ApiClientImpl implements WorkspacesClient {

    public WorkspacesClientImpl(HttpRequestFactory httpRequestFactory, ApiEntityContext entityContext) {
        super(httpRequestFactory, entityContext);
    }

    private EntityDeserializer<WorkspaceImpl> getWorkspaceDeserializer() {
        return getEntityContext().getDeserializer(WorkspaceImpl.class);
    }

    @Override
    public Workspace getWorkspaceById(long id, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("workspaces/" + id)
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
                .asApiObject(getWorkspaceDeserializer());
    }

    @Override
    public PagedList<Workspace> getWorkspaces(RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("workspaces")
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
                .asApiCollection(getWorkspaceDeserializer());
    }
}
