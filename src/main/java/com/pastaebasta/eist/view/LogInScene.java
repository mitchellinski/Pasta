package com.pastaebasta.eist.view;

import com.pastaebasta.eist.controller.ClientController;
import com.pastaebasta.eist.model.Customer;
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


public class LogInScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;

    public LogInScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.clientController = clientController;
        this.application = application;

        Label headline = new Label("Log In");
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

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(passwordLabel, 0, 2);
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        GridPane.setConstraints(passwordField, 1, 2);
        Label emptyPassword = new Label("Password cannot be empty");
        emptyPassword.setFont(new Font("Arial", 20));
        emptyPassword.setTextFill(Color.RED);
        GridPane.setConstraints(emptyPassword, 1, 3);
        emptyPassword.setVisible(false);
        Label wrongPassword = new Label("Wrong Password");
        wrongPassword.setFont(new Font("Arial", 20));
        wrongPassword.setTextFill(Color.RED);
        GridPane.setConstraints(wrongPassword, 1, 3);
        wrongPassword.setVisible(false);


        Button logInButton = new Button("Log In");
        logInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                emptyUsername.setVisible(false);
                emptyPassword.setVisible(false);
                wrongPassword.setVisible(false);
                if (usernameField.getText() != null && passwordField.getText() != null) {
                    String username = usernameField.getText().trim();
                    String password = passwordField.getText().trim();
                    if (username.isEmpty() || password.isEmpty()) {
                        if (username.isEmpty()) {
                            emptyUsername.setVisible(true);
                        }
                        if (password.isEmpty()) {
                            emptyPassword.setVisible(true);
                        }
                    } else {

                        Customer customer = clientController.getCustomerByUsername(username);

                        if (application.getArgon2PasswordEncoder().matches(password, customer.getCustomerPassword())) {
                            application.setCustomer(customer);
                            application.showHomeScene();
                        } else {
                            wrongPassword.setVisible(true);
                        }
                    }
                }


            }
        });

        Label registerLabel = new Label("Not registered yet?");
        registerLabel.setFont(new Font("Arial", 20));
        registerLabel.setPadding(new Insets(20, 0, 0, 0));

        Button registerButton = new Button("Register");
        registerButton.setOnAction(event -> application.showRegisterScene());

        grid.getChildren().addAll(usernameLabel, usernameField, emptyUsername, passwordLabel, passwordField, emptyPassword, wrongPassword);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid, logInButton, registerLabel, registerButton, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        setRoot(vBox);
    }
}
