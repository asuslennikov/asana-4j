package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.TeamsClient;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Team;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.ProjectImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TeamImpl;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityResponseReceiver;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class TeamsApiClientImpl extends ApiClientImpl implements TeamsClient {

    public TeamsApiClientImpl(HttpRequestFactory httpRequestFactory, ApiEntityContext entityContext) {
        super(httpRequestFactory, entityContext);
    }

    private EntityDeserializer<TeamImpl> getTeamDeserializer() {
        return getEntityContext().getDeserializer(TeamImpl.class);
    }

    @Override
    public Team getTeamById(long teamId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("teams/" + String.valueOf(teamId))
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
                .asApiObject(getTeamDeserializer());
    }

    @Override
    public PagedList<Team> getTeamsInOrganisation(long organizationId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("organizations/" + organizationId + "/teams")
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
                .asApiCollection(getTeamDeserializer());
    }

    @Override
    public PagedList<User> getTeamUsers(long teamId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("/teams/" + teamId + "/users")
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
                .asApiCollection(getEntityContext().getDeserializer(UserImpl.class));
    }

    @Override
    public User addUserToTeam(long teamId, long userId) {
        return addUserInternal(teamId, userId);
    }

    @Override
    public User addUserToTeam(long teamId, String email) {
        return addUserInternal(teamId, email);
    }

    @Override
    public User addCurrentUserToTeam(long teamId) {
        return addUserInternal(teamId, "me");
    }

    private User addUserInternal(long teamId, Object userReference) {
        return getEntityContext().newRequest()
                .setUrl("teams/" + teamId + "/addUser")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("user", userReference.toString())
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute(new ApiEntityResponseReceiver())
                .asApiObject(getEntityContext().getDeserializer(UserImpl.class));
    }

    @Override
    public void removeUserFromTeam(long teamId, long userId) {
        removeUserInternal(teamId, userId);
    }

    @Override
    public void removeUserFromTeam(long teamId, String email) {
        removeUserInternal(teamId, email != null ? email : "");
    }

    @Override
    public void removeCurrentUserFromTeam(long teamId) {
        removeUserInternal(teamId, "me");
    }

    private void removeUserInternal(long teamId, Object userReference) {
        getEntityContext().newRequest()
                .setUrl("teams/" + teamId + "/removeUser")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("user", userReference.toString())
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute(new ApiEntityResponseReceiver());
    }

    @Override
    public Project createProject(long teamId, String name) {
        SimpleFieldsUpdater fieldsUpdater = new SimpleFieldsUpdater()
                .setField("name", name);
        return getEntityContext().newRequest()
                .setUrl("teams/" + teamId + "/projects")
                .setEntity(fieldsUpdater.wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute(new ApiEntityResponseReceiver())
                .asApiObject(getEntityContext().getDeserializer(ProjectImpl.class));
    }
}
