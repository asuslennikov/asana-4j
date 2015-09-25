package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.api.UserApiClient;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.auth.AuthenticationProperties;
import ru.jewelline.asana4j.auth.AuthenticationType;
import ru.jewelline.asana4j.core.impl.api.UserApiClientImpl;

public class AsanaExample {
    public static void main(String[] args) {
        AsanaServiceLocator serviceLocator = new AsanaServiceLocator();
        serviceLocator.getAuthenticationService().setAuthenticationType(AuthenticationType.BASIC);
        // Insert your API key here
        serviceLocator.getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.API_KEY, "aHlJ25hc.9HdyPs3sDkQ31WSui95vWtE");
        UserApiClient userApiClient = new UserApiClientImpl(serviceLocator);
        User currentUser = userApiClient.getCurrentUser();
        System.out.println(currentUser);
        if (currentUser != null && !currentUser.getWorkspaces().isEmpty()){
            for (Workspace workspace : currentUser.getWorkspaces()) {
                System.out.println("Workspace for current user is: " + workspace);
            }
        }
    }
}
