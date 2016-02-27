package ru.jewelline.asana4j.examples;

import ru.jewelline.asana4j.Asana;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.auth.AuthenticationProperty;
import ru.jewelline.asana4j.auth.AuthenticationType;
import ru.jewelline.asana4j.core.impl.AsanaImpl;
import ru.jewelline.asana4j.utils.Base64;

public class AuthByPersonalTokenExample {
    public static void main(String[] args) {
        Asana asana = new AsanaImpl() {
            @Override
            public Base64 getBase64Tool() {
                return null; // We don't need the base 64 tool
            }
        };
        asana.getAuthenticationService().setAuthenticationType(AuthenticationType.PERSONAL_ACCESS_TOKEN);
        // Change to correct personal token
        asana.getAuthenticationService()
                .setAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN, "0/e085aec8935735ebfe43a654286f46e5");

        User currentUser = asana.getUsersClient().getCurrentUser();
        System.out.println("You are logged as: " + currentUser.getName()
                + ", email: " + currentUser.getEmail());
    }
}
