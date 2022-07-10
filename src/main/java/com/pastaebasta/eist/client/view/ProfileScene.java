package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class ProfileScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;

    public ProfileScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.clientController = clientController;
        this.application = application;

        Customer customer = application.getCustomer();

        Label headline = new Label("My Profile");
        headline.setFont(new Font("Arial", 40));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label username = new Label("Username:");
        username.setFont(new Font("Arial", 20));
        GridPane.setConstraints(username, 0, 0);
        Label usernameValue = new Label(customer.getCustomerUsername());
        usernameValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(usernameValue, 1, 0);

        Label email = new Label("E-Mail:");
        email.setFont(new Font("Arial", 20));
        GridPane.setConstraints(email, 0, 1);
        Label emailValue = new Label(customer.getCustomerEmail());
        emailValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(emailValue, 1, 1);

        Label password = new Label("Password:");
        password.setFont(new Font("Arial", 20));
        GridPane.setConstraints(password, 0, 2);
        Label passwordValue = new Label("********");
        passwordValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(passwordValue, 1, 2);

        Button changeButton = new Button("Change");
        changeButton.setOnAction(event -> application.showSettingsScene());
        changeButton.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(username, usernameValue, email, emailValue, password, passwordValue);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid, changeButton, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
