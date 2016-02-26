package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.clients.ProjectApiClient;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.ProjectImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class ProjectApiClientImpl extends ApiClientImpl implements ProjectApiClient {

    public ProjectApiClientImpl(HttpRequestFactory httpRequestFactory, ApiEntityContext entityContext) {
        super(HttpRequestFactory, entityContext);
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
                .setUrl("projects")
                .setEntity(fieldsUpdater.wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getProjectDeserializer());
    }

    @Override
    public Project getProjectById(long projectId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("projects/" + projectId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getProjectDeserializer());
    }

    @Override
    public void deleteProject(long projectId, RequestModifier... requestModifiers) {
        newRequest(requestModifiers)
                .setUrl("projects/" + projectId)
                .buildAs(HttpMethod.DELETE)
                .execute();
    }
}
