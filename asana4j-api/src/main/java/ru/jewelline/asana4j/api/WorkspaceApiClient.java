package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.entity.Workspace;

import java.util.List;

public interface WorkspaceApiClient {
    Workspace getWorkspaceById(long id);
    List<Workspace> getWorkspaces();
    // TODO typeahead search
}
