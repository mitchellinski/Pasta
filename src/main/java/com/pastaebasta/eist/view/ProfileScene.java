package com.pastaebasta.eist.view;

import com.pastaebasta.eist.controller.ClientController;
import com.pastaebasta.eist.model.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class ProfileScene extends Scene {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 500;

    private final ClientController clientController;
    private final ViewApplication application;

    public ProfileScene(ClientController clientController, ViewApplication viewApplication) {
        super(new VBox(), WIDTH, HEIGHT);
        this.clientController = clientController;
        this.application = viewApplication;

        Customer customer = application.getCustomer();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label headline = new Label("My Profile");
        headline.setFont(new Font("Arial", 20));

        Label username = new Label("Username");
        username.setFont(new Font("Arial", 10));
        GridPane.setConstraints(username, 0, 0);
        Label usernameValue = new Label(customer.getCustomerUsername());
        usernameValue.setFont(new Font("Arial", 10));
        GridPane.setConstraints(usernameValue, 1, 0);

        Label email = new Label("E-Mail");
        email.setFont(new Font("Arial", 10));
        GridPane.setConstraints(email, 0, 1);
        Label emailValue = new Label(customer.getCustomerEmail());
        emailValue.setFont(new Font("Arial", 10));
        GridPane.setConstraints(emailValue, 1, 1);

        Label password = new Label("Password");
        password.setFont(new Font("Arial", 10));
        GridPane.setConstraints(password, 0, 2);
        Label passwordValue = new Label("********");
        passwordValue.setFont(new Font("Arial", 10));
        GridPane.setConstraints(passwordValue, 1, 2);

        Button changeButton = new Button("Change");
        changeButton.setOnAction(event -> application.showSettingsScene());
        GridPane.setRowIndex(changeButton, 3);

        grid.getChildren().addAll(username, usernameValue, email, emailValue, password, passwordValue, changeButton);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
