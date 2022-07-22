package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Customer;
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

/**
 * Scene - Edit Username
 *
 * This class is for a user to edit their username of their account, which propagates into the backend server
 */
public class EditUsernameScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;

    public EditUsernameScene(ClientController clientController, ViewApplication application) {
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

        Label username = new Label("New Username:");
        username.setFont(new Font("Arial", 20));
        GridPane.setConstraints(username, 0, 0);
        TextField usernameField = new TextField(customer.getCustomerUsername());
        usernameField.setPromptText("new Username");
        GridPane.setConstraints(usernameField, 1, 0);
        Label emptyUsername = new Label("Username cannot be empty");
        emptyUsername.setFont(new Font("Arial", 20));
        emptyUsername.setTextFill(Color.RED);
        emptyUsername.setVisible(false);
        GridPane.setConstraints(emptyUsername, 1, 1);
        Label takenUsername = new Label("Username is already taken");
        takenUsername.setFont(new Font("Arial", 20));
        takenUsername.setTextFill(Color.RED);
        GridPane.setConstraints(takenUsername, 1, 1);
        takenUsername.setVisible(false);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> application.showProfileScene());
        GridPane.setConstraints(cancelButton, 0, 2);

        Button changeButton = new Button("Change");
        changeButton.setOnAction(event -> {
            emptyUsername.setVisible(false);
            takenUsername.setVisible(false);
            if (usernameField.getText() != null) {
                String newUsername = usernameField.getText().trim();
                if (newUsername.isEmpty()) {
                    usernameField.clear();
                    emptyUsername.setVisible(true);
                } else {
                    if (newUsername.equals(customer.getCustomerUsername())) {
                        application.showProfileScene();
                    }
                    try {
                        clientController.getCustomerByUsername(newUsername);
                        takenUsername.setVisible(true);
                    } catch (RuntimeException e) {
                        customer.setCustomerUsername(newUsername);
                        application.setCustomer(clientController.updateCustomer(customer));
                        application.showProfileScene();
                    }
                }
            }
        });
        GridPane.setConstraints(changeButton, 1, 2);

        grid.getChildren().addAll(username, usernameField, emptyUsername, takenUsername, cancelButton, changeButton);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
