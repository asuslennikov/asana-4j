package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Objects;

public class WorkspaceApiClientImpl extends ApiClientImpl<Workspace, WorkspaceImpl> implements WorkspaceApiClient {

    public WorkspaceApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
    }

    @Override
    public WorkspaceImpl newInstance() {
        return new WorkspaceImpl(this);
    }

    @Override
    public Workspace getWorkspaceById(long id, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("workspaces/" + id)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject();
    }

    @Override
    public PagedList<Workspace> getWorkspaces(RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("workspaces/")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection();
    }

    @Override
    public Workspace update(Workspace workspace, RequestModifier... requestModifiers) {
        Objects.requireNonNull(workspace);
        return newRequest(requestModifiers)
                .path("workspaces/" + workspace.getId())
                .setEntity(workspace)
                .buildAs(HttpMethod.PUT)
                .execute()
                .asApiObject();
    }
}
