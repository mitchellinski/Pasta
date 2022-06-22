package com.pastaebasta.eist.view;

import com.pastaebasta.eist.controller.ClientController;
import com.pastaebasta.eist.model.Customer;
import javafx.application.Platform;
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
import reactor.core.publisher.Mono;

import java.lang.management.PlatformLoggingMXBean;
import java.util.Objects;


public class LogInScene extends Scene {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 500;

    private final ClientController clientController;
    private final ViewApplication application;
    private Customer customer;

    public LogInScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), WIDTH, HEIGHT);
        this.clientController = clientController;
        this.application = application;

        Label headline = new Label("Log In");
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

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(passwordLabel, 0, 1);
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        GridPane.setConstraints(passwordField, 1, 1);


        Button logInButton = new Button("Log In");
        logInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (usernameField.getText() != null && !usernameField.getText().isEmpty()) {
                    if(passwordField.getText() != null && !passwordField.getText().isEmpty()) {
                        // TODO Log In
                        Customer customer = clientController.getCustomer("1");
                        if(Objects.equals(customer.getCustomerUsername(), usernameField.getText().trim()) && customer.getPassword().equals(passwordField.getText().trim())) {
                            application.setCustomer(customer);
                            application.showHomeScene();
                        }

                    } else {
                        // No Password
                    }
                } else {
                    // No Username
                }
            }

            private void setCustomer(Customer newCustomer) {
                Platform.runLater(() -> customer = newCustomer);
            }
        });

        Label registerLabel = new Label("Not registered yet?");
        registerLabel.setFont(new Font("Arial", 15));
        registerLabel.setPadding(new Insets(20, 0, 0, 0));

        Button registerButton = new Button("Register");
        registerButton.setOnAction(event -> application.showRegisterScene());

        grid.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid, logInButton, registerLabel, registerButton, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        setRoot(vBox);
    }
}
