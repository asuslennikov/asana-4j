package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

public class WorkspaceApiClientImpl extends ApiClientImpl<WorkspaceImpl> implements WorkspaceApiClient {

    private final ApiEntityDeserializer<WorkspaceImpl> workspaceDeserializer;

    public WorkspaceApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
        this.workspaceDeserializer = new ApiEntityDeserializer<>(this);
    }

    @Override
    public WorkspaceImpl getInstance() {
        return new WorkspaceImpl(this);
    }

    @Override
    public Workspace getWorkspaceById(long id, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("workspaces/" + id)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(this.workspaceDeserializer);
    }

    @Override
    public PagedList<Workspace> getWorkspaces(RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("workspaces/")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(this.workspaceDeserializer);
    }
}
