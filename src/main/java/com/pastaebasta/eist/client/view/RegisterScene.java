package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RegisterScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;

    public RegisterScene(ClientController clientController, ViewApplication application) {

        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());

        this.clientController = clientController;
        this.application = application;

        Label headline = new Label("Register");
        headline.setFont(new Font("Arial", 20));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(5);

        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        GridPane.setConstraints(usernameField, 1, 0);
        Label emptyUsername = new Label("Username cannot be empty");
        emptyUsername.setFont(new Font("Arial", 20));
        emptyUsername.setTextFill(Color.RED);
        GridPane.setConstraints(emptyUsername, 1, 1);
        emptyUsername.setVisible(false);
        Label takenUsername = new Label("Username is already taken");
        takenUsername.setFont(new Font("Arial", 20));
        takenUsername.setTextFill(Color.RED);
        GridPane.setConstraints(takenUsername, 1, 1);
        takenUsername.setVisible(false);

        Label emailLabel = new Label("E-Mail");
        emailLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(emailLabel, 0, 2);
        TextField emailField = new TextField();
        emailField.setPromptText("E-Mail");
        GridPane.setConstraints(emailField, 1, 2);
        Label emptyEmail = new Label("E-Mail cannot be empty");
        emptyEmail.setFont(new Font("Arial", 20));
        emptyEmail.setTextFill(Color.RED);
        GridPane.setConstraints(emptyEmail, 1, 3);
        emptyEmail.setVisible(false);
        Label takenEmail = new Label("E-Mail is already registered");
        takenEmail.setFont(new Font("Arial", 20));
        takenEmail.setTextFill(Color.RED);
        GridPane.setConstraints(takenEmail, 1, 3);
        takenEmail.setVisible(false);

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(passwordLabel, 0, 4);
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        GridPane.setConstraints(passwordField, 1, 4);
        Label emptyPassword = new Label("Password cannot be empty");
        emptyPassword.setFont(new Font("Arial", 20));
        emptyPassword.setTextFill(Color.RED);
        GridPane.setConstraints(emptyPassword, 1, 5);
        emptyPassword.setVisible(false);


        Button registerButton = new Button("Register");
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                emptyUsername.setVisible(false);
                emptyEmail.setVisible(false);
                emptyPassword.setVisible(false);
                takenUsername.setVisible(false);
                takenEmail.setVisible(false);
                if (usernameField.getText() != null && emailField.getText() != null && passwordField.getText() != null) {
                    String username = usernameField.getText().trim();
                    String email = emailField.getText().trim();
                    String password = passwordField.getText().trim();
                    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        if (username.isEmpty()) {
                            emptyUsername.setVisible(true);
                        }
                        if (email.isEmpty()) {
                            emptyEmail.setVisible(true);
                        }
                        if (password.isEmpty()) {
                            emptyPassword.setVisible(true);
                        }
                    } else {
                        try {
                            clientController.getCustomerByUsername(username);
                            takenUsername.setVisible(true);
                        } catch (RuntimeException usernameException) {
                            try {
                                clientController.getCustomerByEmail(email);
                                takenEmail.setVisible(true);
                            } catch (RuntimeException emailException) {
                                String encodedPassword = application.getArgon2PasswordEncoder().encode(password);
                                Customer newCustomer = new Customer(username, email, encodedPassword);
                                newCustomer = clientController.addCustomer(newCustomer);
                                application.setCustomer(newCustomer);
                                application.showHomeScene();
                            }
                        }
                    }
                }
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameField, emptyUsername, takenUsername, emailLabel, emailField, emptyEmail, takenEmail, passwordLabel, passwordField, emptyPassword);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid, registerButton, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
