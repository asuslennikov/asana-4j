package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;
import ru.jewelline.asana4j.http.HttpMethod;

public class WorkspaceApiClientImpl extends ApiClientImpl implements WorkspaceApiClient {

    public WorkspaceApiClientImpl(RequestFactory requestFactory) {
        super(requestFactory);
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
