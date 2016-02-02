package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.modifiers.RequestModifier;

public interface WorkspaceApiClient {
    Workspace getWorkspaceById(long id, RequestModifier... requestModifiers);
    PagedList<Workspace> getWorkspaces(RequestModifier... requestModifiers);
    // TODO typeahead search
}
