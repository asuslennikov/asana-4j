package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.request.http.modifiers.RequestModifier;

public interface WorkspacesClient {
    Workspace getWorkspaceById(long id, RequestModifier... requestModifiers);

    PagedList<Workspace> getWorkspaces(RequestModifier... requestModifiers);
    // TODO typeahead search
}
