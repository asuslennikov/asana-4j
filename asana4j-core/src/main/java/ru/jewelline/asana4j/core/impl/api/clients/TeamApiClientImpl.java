package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.TeamClientApi;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Team;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.ProjectImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TeamImpl;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class TeamApiClientImpl extends ApiClientImpl implements TeamClientApi {

    public TeamApiClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<TeamImpl> getTeamDeserializer() {
        return getEntityContext().getDeserializer(TeamImpl.class);
    }

    @Override
    public Team getTeamById(long teamId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("teams/" + String.valueOf(teamId))
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getTeamDeserializer());
    }

    @Override
    public PagedList<Team> getTeamsInOrganisation(long organizationId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("organizations/" + organizationId + "/teams")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getTeamDeserializer());
    }

    @Override
    public PagedList<User> getTeamUsers(long teamId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("/teams/" + teamId + "/users")
                .buildAs(HttpMethod.GET)
                .execute()
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
                .execute()
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
                .execute();
    }

    @Override
    public Project createProject(long teamId, String name) {
        SimpleFieldsUpdater fieldsUpdater = new SimpleFieldsUpdater()
                .setField("name", name);
        return getEntityContext().newRequest()
                .setUrl("teams/" + teamId + "/projects")
                .setEntity(fieldsUpdater.wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getEntityContext().getDeserializer(ProjectImpl.class));
    }
}
