package ru.jewelline.asana4j.examples;

import ru.jewelline.asana4j.Asana;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.clients.UsersClient;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.auth.AuthenticationProperty;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.auth.AuthenticationType;
import ru.jewelline.asana4j.core.impl.AsanaImpl;
import ru.jewelline.asana4j.utils.Base64;

import java.awt.*;
import java.net.URI;
import java.util.Scanner;

public class AuthByOAuthExample {
    public static void main(String[] args) throws Exception {
        Asana asana = new AsanaImpl() {
            @Override
            public Base64 getBase64Tool() {
                return null; // We don't need the base 64 tool
            }
        };
        FileBasedStore store = new FileBasedStore();

        AuthenticationService authServ = asana.getAuthenticationService();
        authServ.load(store);
        authServ.setAuthenticationType(AuthenticationType.GRANT_CODE);
        authServ.setAuthenticationProperty(AuthenticationProperty.CLIENT_ID, "38537974419952");
        authServ.setAuthenticationProperty(AuthenticationProperty.CLIENT_SECRET, "606e261556cfc0f4f354917e25459e79");
        authServ.setAuthenticationProperty(AuthenticationProperty.AUTHORIZATION_ENDPOINT_REDIRECT_URL, "urn:ietf:wg:oauth:2.0:oob");

        if (!authServ.isAuthenticated()) {
            System.out.println("You will be redirected to authentication endpoint: ");
            String oAuthUrl = authServ.getOAuthUserEndPoint();
            System.out.println(oAuthUrl);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(oAuthUrl));
            } else {
                System.out.println("Please open the url above in your browser.");
            }
            System.out.println("Paste the access code from the browser here...");
            authServ.setAuthenticationProperty(AuthenticationProperty.ACCESS_CODE, new Scanner(System.in).nextLine());
            authServ.authenticate();
            authServ.save(store);
        }

        UsersClient usersClient = asana.getUsersClient();
        User currentUser = null;
        try {
            currentUser = usersClient.getCurrentUser();
        } catch (ApiException ae) {
            // Access token has expired
            if (ae.getErrorCode() == 401) {
                System.out.println("Access token has expired, refreshing...");
                authServ.authenticate();
                currentUser = usersClient.getCurrentUser();
            }
        }
        if (currentUser != null) {
            System.out.println("You are logged as: " + currentUser.getName()
                    + ", email: " + currentUser.getEmail());
        }
    }
}
