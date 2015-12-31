package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.Pagination;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.auth.AuthenticationProperties;
import ru.jewelline.asana4j.auth.AuthenticationType;

public class AsanaExample {
    public static void main(String[] args) {
        AsanaContext asanaContext = new AsanaContext();
        asanaContext.getAuthenticationService().setAuthenticationType(AuthenticationType.BASIC);
        asanaContext.getAuthenticationService()
                .setAuthenticationProperty(AuthenticationProperties.API_KEY, "aHlJ25hc.9HdyPs3sDkQ31WSui95vWtE"); // Insert your API key here

        UserApiClient userClient = asanaContext.getUserClient();
        System.out.println(userClient.getCurrentUser());

        WorkspaceApiClient workspaceClient = asanaContext.getWorkspaceClient();
        for (Workspace workspace : workspaceClient.getWorkspaces(Pagination.FIRST_PAGE)) {
            System.out.println(workspace);
            for (Project project : workspace.getProjects()) {
                System.out.println(project);
            }
        }
    }
}
