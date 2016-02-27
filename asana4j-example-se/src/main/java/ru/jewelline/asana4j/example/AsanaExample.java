package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.Asana;
import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.Pagination;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.auth.AuthenticationProperty;
import ru.jewelline.asana4j.auth.AuthenticationType;
import ru.jewelline.asana4j.core.impl.AsanaImpl;
import ru.jewelline.asana4j.se.Base64JavaSeUtil;
import ru.jewelline.asana4j.utils.Base64;

public class AsanaExample {
    public static void main(String[] args) {
        Asana asana = new AsanaImpl() {
            @Override
            public Base64 getBase64Tool() {
                return new Base64JavaSeUtil();
            }
        };
        asana.getAuthenticationService().setAuthenticationType(AuthenticationType.BASIC);
        asana.getAuthenticationService()
                .setAuthenticationProperty(AuthenticationProperty.API_KEY, "aHlJ25hc.9HdyPs3sDkQ31WSui95vWtE"); // Insert your API key here

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
