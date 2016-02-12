package ru.jewelline.asana4j.core.impl.clients;

import ru.jewelline.asana4j.core.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.core.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.entity.WorkspaceImpl;
import ru.jewelline.asana4j.core.impl.entity.common.ApiEntityContext;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.entity.EntityDeserializer;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

public class WorkspaceApiClientImpl extends ApiClientImpl implements WorkspaceApiClient {

    public WorkspaceApiClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<WorkspaceImpl> getWorkspaceDeserializer() {
        return getEntityContext().getDeserializer(WorkspaceImpl.class);
    }

    @Override
    public Workspace getWorkspaceById(long id, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("workspaces/" + id)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getWorkspaceDeserializer());
    }

    @Override
    public PagedList<Workspace> getWorkspaces(RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("workspaces")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getWorkspaceDeserializer());
    }
}
