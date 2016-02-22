package ru.jewelline.asana4j.impl.clients;

import ru.jewelline.asana4j.api.beans.ProjectBean;
import ru.jewelline.asana4j.api.clients.ProjectsClient;
import ru.jewelline.asana4j.api.entities.Project;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.impl.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.api.entity.EntityDeserializer;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

public class ProjectsClientImpl extends ApiClientImpl implements ProjectsClient {

    public ProjectsClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<ProjectBean> getProjectDeserializer() {
        return getEntityContext().getDeserializer(ProjectBean.class);
    }

    @Override
    public Project create(long workspaceId, String projectName) {
        SimpleFieldsUpdater fieldsUpdater = new SimpleFieldsUpdater()
                .setField("workspace", workspaceId)
                .setField("name", projectName);
        return apiRequest()
                .path("projects")
                .setEntity(fieldsUpdater.wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getProjectDeserializer());
    }

    @Override
    public Project getProjectById(long projectId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("projects/" + projectId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getProjectDeserializer());
    }

    @Override
    public void deleteProject(long projectId, RequestModifier... requestModifiers) {
        apiRequest(requestModifiers)
                .path("projects/" + projectId)
                .buildAs(HttpMethod.DELETE)
                .execute();
    }
}
