package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.api.params.QueryParameter;

public interface WorkspaceApiClient {
    Workspace getWorkspaceById(long id, QueryParameter... params);
    PagedList<Workspace> getWorkspaces(QueryParameter... params);
    // TODO typeahead search
}
