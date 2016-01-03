package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.clients.ProjectApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.ProjectImpl;
import ru.jewelline.asana4j.core.impl.api.entity.io.CachedJsonEntity;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.HashMap;

public class ProjectApiClientImpl extends ApiClientImpl implements ProjectApiClient {

    public ProjectApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
    }

    private EntityDeserializer<ProjectImpl> getProjectDeserializer() {
        return getEntityContext().getDeserializer(ProjectImpl.class);
    }

    @Override
    public Project create(long workspaceId, String projectName) {
        return newRequest()
                .path("projects")
                .setEntity(new CachedJsonEntity(new HashMap<String, Object>()))
                .setQueryParameter("workspace", String.valueOf(workspaceId))
                .setQueryParameter("name", projectName)
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getProjectDeserializer());
    }

    @Override
    public Project getProjectById(long projectId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("projects/" + projectId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getProjectDeserializer());
    }

    @Override
    public void deleteProject(long projectId, RequestModifier... requestModifiers) {
        newRequest(requestModifiers)
                .path("projects/" + projectId)
                .buildAs(HttpMethod.DELETE)
                .execute();
    }
}
