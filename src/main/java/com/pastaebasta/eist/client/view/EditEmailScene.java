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
 * Scene - Edit Email
 *
 * This class is for a user to edit their email of their account, which propagates into the backend server
 */
public class EditEmailScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;

    /**
     * Constructs the initial view for this scene
     * @param clientController
     * @param application
     */
    public EditEmailScene(ClientController clientController, ViewApplication application) {
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

        Label email = new Label("New E-Mail:");
        email.setFont(new Font("Arial", 20));
        GridPane.setConstraints(email, 0, 0);
        TextField emailField = new TextField(customer.getCustomerEmail());
        emailField.setPromptText("new E-Mail");
        GridPane.setConstraints(emailField, 1, 0);
        Label emptyEmail = new Label("E-Mail cannot be empty");
        emptyEmail.setFont(new Font("Arial", 20));
        emptyEmail.setTextFill(Color.RED);
        emptyEmail.setVisible(false);
        GridPane.setConstraints(emptyEmail, 1, 1);
        Label takenEmail = new Label("E-Mail is already registered");
        takenEmail.setFont(new Font("Arial", 20));
        takenEmail.setTextFill(Color.RED);
        GridPane.setConstraints(takenEmail, 1, 1);
        takenEmail.setVisible(false);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> application.showProfileScene());
        GridPane.setConstraints(cancelButton, 0, 2);

        Button changeButton = new Button("Change");
        changeButton.setOnAction(event -> {
            emptyEmail.setVisible(false);
            takenEmail.setVisible(false);
            if (emailField.getText() != null) {
                String newEmail = emailField.getText().trim();
                if (newEmail.isEmpty()) {
                    emailField.clear();
                    emptyEmail.setVisible(true);
                } else {
                    if (newEmail.equals(customer.getCustomerEmail())) {
                        application.showProfileScene();
                    }
                    try {
                        clientController.getCustomerByEmail(newEmail);
                        takenEmail.setVisible(true);
                    } catch (RuntimeException e) {
                        customer.setCustomerEmail(newEmail);
                        clientController.updateCustomer(customer);
                        application.showProfileScene();
                    }
                }
            }
        });
        GridPane.setConstraints(changeButton, 1, 2);

        grid.getChildren().addAll(email, emailField, emptyEmail, takenEmail, cancelButton, changeButton);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
