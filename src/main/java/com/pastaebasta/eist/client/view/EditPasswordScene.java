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

public class EditPasswordScene extends Scene {

    private ClientController clientController;
    private ViewApplication application;

    public EditPasswordScene(ClientController clientController, ViewApplication application) {
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

        Label oldPassword = new Label("Old Password:");
        oldPassword.setFont(new Font("Arial", 20));
        GridPane.setConstraints(oldPassword, 0, 0);
        TextField oldPasswordField = new TextField();
        oldPasswordField.setPromptText("old Password");
        GridPane.setConstraints(oldPasswordField, 0, 1);
        Label oldEmptyPassword = new Label("Old Password cannot be blank");
        oldEmptyPassword.setFont(new Font("Arial", 20));
        oldEmptyPassword.setTextFill(Color.RED);
        oldEmptyPassword.setVisible(false);
        GridPane.setConstraints(oldEmptyPassword, 0, 2);
        Label wrongPassword = new Label("Wrong Password");
        wrongPassword.setFont(new Font("Arial", 20));
        wrongPassword.setTextFill(Color.RED);
        GridPane.setConstraints(wrongPassword, 0, 2);
        wrongPassword.setVisible(false);

        Label newPassword = new Label("New Password");
        newPassword.setFont(new Font("Arial", 20));
        GridPane.setConstraints(newPassword, 1, 0);
        TextField newPasswordField = new TextField();
        newPasswordField.setPromptText("new Password");
        GridPane.setConstraints(newPasswordField, 1, 1);
        Label newEmptyPassword = new Label("New Password cannot be blank");
        newEmptyPassword.setFont(new Font("Arial", 20));
        newEmptyPassword.setTextFill(Color.RED);
        newEmptyPassword.setVisible(false);
        GridPane.setConstraints(newEmptyPassword, 1, 2);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> application.showProfileScene());
        GridPane.setConstraints(cancelButton, 0, 3);

        Button changeButton = new Button("Change");
        changeButton.setOnAction(event -> {
            oldEmptyPassword.setVisible(false);
            wrongPassword.setVisible(false);
            newEmptyPassword.setVisible(false);
            if (oldPasswordField.getText() != null && newPasswordField.getText() != null) {
                String oldPasswordValue = oldPasswordField.getText().trim();
                String newPasswordValue = newPasswordField.getText().trim();
                if (oldPasswordValue.isEmpty() || newPasswordValue.isEmpty()) {
                    if (oldPasswordValue.isEmpty()) {
                        oldPasswordField.clear();
                        oldEmptyPassword.setVisible(true);
                    }
                    if (newPasswordValue.isEmpty()) {
                        newPasswordField.clear();
                        newEmptyPassword.setVisible(true);
                    }
                } else {
                    if (application.getArgon2PasswordEncoder().matches(oldPasswordValue, customer.getCustomerPassword())) {
                        String encodedPassword = application.getArgon2PasswordEncoder().encode(newPasswordValue);
                        customer.setCustomerPassword(encodedPassword);
                        clientController.updateCustomer(customer);
                        application.showProfileScene();
                        application.showProfileScene();
                    } else {
                        wrongPassword.setVisible(true);
                    }

                }
            }
        });
        GridPane.setConstraints(changeButton, 1, 3);

        grid.getChildren().addAll(oldPassword, oldPasswordField, oldEmptyPassword, wrongPassword,
                newPassword, newPasswordField, newEmptyPassword, cancelButton, changeButton);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
