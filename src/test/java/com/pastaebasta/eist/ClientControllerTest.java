package com.pastaebasta.eist;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.client.view.ViewApplication;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class ClientControllerTest {
    private final ClientController clientController;
    private Customer customer;

    public ClientControllerTest() {
        clientController = new ClientController();
    }

    public Customer getCustomerTest() {
        return null;
    }

    public static void main(String[] args) {
        ViewApplication application = new ViewApplication();
        Argon2PasswordEncoder code = application.getArgon2PasswordEncoder();
        String raw = "passwort";
        String encoded = code.encode(raw);
        System.out.println(code.encode(encoded));
        System.out.println(code.matches(raw, encoded));
    }
}
