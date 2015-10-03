package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.WorkspaceApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.Pagination;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.auth.AuthenticationProperties;
import ru.jewelline.asana4j.auth.AuthenticationType;
import ru.jewelline.asana4j.core.impl.api.clients.WorkspaceApiClientImpl;

public class AsanaExample {
    public static void main(String[] args) {
        AsanaServiceLocator serviceLocator = new AsanaServiceLocator();
        serviceLocator.getAuthenticationService().setAuthenticationType(AuthenticationType.BASIC);
        // Insert your API key here
        serviceLocator.getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.API_KEY, "aHlJ25hc.9HdyPs3sDkQ31WSui95vWtE");
/*        UserApiClient userApiClient = new UserApiClientImpl(serviceLocator.getAuthenticationService(), serviceLocator.getHttpClient());
        User currentUser = userApiClient.getCurrentUser();
        System.out.println(currentUser);
        if (currentUser != null && currentUser.getWorkspaces() != null){
            for (Workspace workspace : currentUser.getWorkspaces()) {
                System.out.println("Workspace for current user is: " + workspace);
            }
        }*/
        WorkspaceApiClient workspaceApiClient = new WorkspaceApiClientImpl(serviceLocator.getAuthenticationService(), serviceLocator.getHttpClient());
        PagedList<Workspace> page1 = workspaceApiClient.getWorkspaces(new Pagination(1, null));
        System.out.println(page1);
        if (page1.hasNextPage()) {
            PagedList<Workspace> page2 = workspaceApiClient.getWorkspaces(page1.getNextPageModifier());
            System.out.println(page2);
        }
    }
}
