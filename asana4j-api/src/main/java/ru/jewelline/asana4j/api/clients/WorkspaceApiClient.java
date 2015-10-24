package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Workspace;

public interface WorkspaceApiClient {
    Workspace getWorkspaceById(long id, RequestModifier... requestModifiers);
    PagedList<Workspace> getWorkspaces(RequestModifier... requestModifiers);

    Workspace update(Workspace workspace, RequestModifier... requestModifiers);
    // TODO typeahead search
}
