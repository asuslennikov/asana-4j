package ru.jewelline.asana4j.examples;

import ru.jewelline.asana4j.Asana;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.auth.AuthenticationProperty;
import ru.jewelline.asana4j.auth.AuthenticationType;
import ru.jewelline.asana4j.core.impl.AsanaImpl;
import ru.jewelline.asana4j.utils.Base64;

public class AuthByApiKeyExample {
    public static void main(String[] args) {
        Asana asana = new AsanaImpl() {
            @Override
            public Base64 getBase64Tool() {
                return new Base64Tool();
            }
        };
        asana.getAuthenticationService().setAuthenticationType(AuthenticationType.BASIC);
        // Change to correct API key
        asana.getAuthenticationService()
                .setAuthenticationProperty(AuthenticationProperty.API_KEY, "Your API key here");

        User currentUser = asana.getUsersClient().getCurrentUser();
        System.out.println("You are logged as: " + currentUser.getName()
                + ", email: " + currentUser.getEmail());
    }
}
