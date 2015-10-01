package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.WorkspaceApiClient;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.api.params.QueryParameter;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

public class WorkspaceApiClientImpl extends ApiClientImpl<Workspace, WorkspaceImpl> implements WorkspaceApiClient {

    public WorkspaceApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
    }

    @Override
    public WorkspaceImpl newInstance() {
        return new WorkspaceImpl();
    }

    @Override
    public Workspace getWorkspaceById(long id, QueryParameter... queryParameters) {
        return newRequest(queryParameters)
                .path("workspaces/" + id)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject();
    }

    @Override
    public PagedList<Workspace> getWorkspaces(QueryParameter... queryParameters) {
        return newRequest(queryParameters)
                .path("workspaces/")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection();
    }
}
