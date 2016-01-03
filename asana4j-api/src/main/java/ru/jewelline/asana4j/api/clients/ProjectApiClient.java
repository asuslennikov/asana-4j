package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Project;

public interface ProjectApiClient {

    // TODO what about organization? need to pass team
    Project create(long workspaceId, String projectName);

    Project getProjectById(long projectId, RequestModifier... requestModifiers);

    void deleteProject(long projectId, RequestModifier... requestModifiers);
}
