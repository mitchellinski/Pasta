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
import javafx.scene.text.Font;


/**
 * SettingsScene represents the settings page, where the client can
 * update their user data.
 * @author Michael
 */
public class SettingsScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;

    /**
     * Constructor
     *
     * Sets up the settings scene and connects buttons to actions within the application.
     * @param clientController ClientController
     * @param application ViewApplicaiton
     */
    public SettingsScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.clientController = clientController;
        this.application = application;

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
        TextField usernameValue = new TextField(customer.getCustomerUsername());
        usernameValue.setPromptText("new Username");
        GridPane.setConstraints(usernameValue, 1, 0);

        Label email = new Label("E-Mail");
        email.setFont(new Font("Arial", 10));
        GridPane.setConstraints(email, 0, 1);
        TextField emailValue = new TextField(customer.getCustomerEmail());
        emailValue.setPromptText("new E-Mail");
        GridPane.setConstraints(emailValue, 1, 1);

        Label password = new Label("Password");
        password.setFont(new Font("Arial", 10));
        GridPane.setConstraints(password, 0, 2);
        TextField passwordValue = new TextField();
        passwordValue.setPromptText("new Password");
        GridPane.setConstraints(passwordValue, 1, 2);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            String newUsername = usernameValue.getText().trim();
            String newEmail = emailValue.getText().trim();
            String newPassword = passwordValue.getText().trim();
            String encodedPassword = application.getArgon2PasswordEncoder().encode(newPassword);
            customer.setCustomerUsername(newUsername);
            customer.setCustomerEmail(newEmail);
            customer.setCustomerPassword(encodedPassword);
            clientController.updateCustomer(customer);
            application.showProfileScene();
        });
        GridPane.setConstraints(saveButton, 0, 3);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> application.showProfileScene());
        GridPane.setConstraints(cancelButton, 1, 3);

        grid.getChildren().addAll(username, usernameValue, email, emailValue, password, passwordValue, saveButton, cancelButton);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
