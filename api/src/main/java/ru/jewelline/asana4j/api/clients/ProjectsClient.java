package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.request.http.modifiers.RequestModifier;

public interface ProjectsClient {

    Project create(long workspaceId, String projectName);

    Project getProjectById(long projectId, RequestModifier... requestModifiers);

    void deleteProject(long projectId, RequestModifier... requestModifiers);
}
