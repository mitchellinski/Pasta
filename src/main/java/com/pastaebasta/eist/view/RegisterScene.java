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
import javafx.scene.text.Font;

public class RegisterScene extends Scene {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 500;

    private final ClientController clientController;
    private final ViewApplication application;

    public RegisterScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), WIDTH, HEIGHT);
        this.clientController = clientController;
        this.application = application;

        Label headline = new Label("Register");
        headline.setFont(new Font("Arial", 20));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(5);

        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        GridPane.setConstraints(usernameField, 1, 0);

        Label emailLabel = new Label("E-Mail");
        emailLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(emailLabel, 0, 1);
        TextField emailField = new TextField();
        emailField.setPromptText("E-Mail");
        GridPane.setConstraints(emailField, 1, 1);

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(passwordLabel, 0, 2);
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        GridPane.setConstraints(passwordField, 1, 2);


        Button registerButton = new Button("Register");
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (usernameField.getText() != null && !usernameField.getText().isEmpty()) {
                    if (emailField.getText() != null && !emailField.getText().isEmpty()) {
                        if(passwordField.getText() != null && !passwordField.getText().isEmpty()) {
                            // TODO create Customer
                        } else {
                            // No Password
                        }
                    } else {
                        // No E-Mail
                    }
                } else {
                    // No Username
                }
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameField, emailLabel, emailField, passwordLabel, passwordField);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid, registerButton, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
