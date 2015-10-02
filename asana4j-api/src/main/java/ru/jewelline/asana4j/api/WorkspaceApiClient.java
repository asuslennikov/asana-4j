package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.api.options.RequestOption;

public interface WorkspaceApiClient {
    Workspace getWorkspaceById(long id, RequestOption... requestOptions);
    PagedList<Workspace> getWorkspaces(RequestOption... requestOptions);
    // TODO typeahead search
}
