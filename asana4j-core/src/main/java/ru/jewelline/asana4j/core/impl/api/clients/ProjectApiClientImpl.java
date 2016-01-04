package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.clients.ProjectApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.ProjectImpl;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

public class ProjectApiClientImpl extends ApiClientImpl implements ProjectApiClient {

    public ProjectApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
    }

    private EntityDeserializer<ProjectImpl> getProjectDeserializer() {
        return getEntityContext().getDeserializer(ProjectImpl.class);
    }

    @Override
    public Project create(long workspaceId, String projectName) {
        SimpleFieldsUpdater fieldsUpdater = new SimpleFieldsUpdater()
                .setField("workspace", workspaceId)
                .setField("name", projectName);
        return newRequest()
                .path("projects")
                .setEntity(fieldsUpdater.wrapFieldsAsEntity())
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
