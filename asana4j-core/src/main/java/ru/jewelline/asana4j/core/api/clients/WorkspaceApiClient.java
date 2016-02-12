package ru.jewelline.asana4j.core.api.clients;

import ru.jewelline.asana.common.PagedList;
import ru.jewelline.asana4j.core.api.entity.Workspace;
import ru.jewelline.request.http.modifiers.RequestModifier;

public interface WorkspaceApiClient {
    Workspace getWorkspaceById(long id, RequestModifier... requestModifiers);
    PagedList<Workspace> getWorkspaces(RequestModifier... requestModifiers);
    // TODO typeahead search
}
