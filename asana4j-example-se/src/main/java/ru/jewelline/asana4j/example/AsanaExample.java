package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.Pagination;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.auth.AuthenticationProperties;
import ru.jewelline.asana4j.auth.AuthenticationType;

public class AsanaExample {
    public static void main(String[] args) {
        Asana asana = new Asana();
        asana.getAuthenticationService().setAuthenticationType(AuthenticationType.BASIC);
        asana.getAuthenticationService()
                .setAuthenticationProperty(AuthenticationProperties.API_KEY, "aHlJ25hc.9HdyPs3sDkQ31WSui95vWtE"); // Insert your API key here

        UserApiClient userClient = asana.getUserClient();
        User currentUser = userClient.getCurrentUser();
        System.out.println(currentUser);

        WorkspaceApiClient workspaceClient = asana.getWorkspaceClient();
        for (Workspace workspace : workspaceClient.getWorkspaces(Pagination.FIRST_PAGE)) {
            System.out.println(workspace);
            for (Project project : workspace.getProjects()) {
                System.out.println(project);
                for (Task task : project.getTasks()) {
                    System.out.println(task);
                }
            }
        }
    }
}
