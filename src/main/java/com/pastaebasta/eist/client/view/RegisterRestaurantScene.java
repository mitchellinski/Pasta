package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.Genre;
import com.pastaebasta.eist.shared.model.PriceCategory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;

/**
 * Scene - Register Restaurant
 *
 *
 *
 * Scene allows a user register a reservation at a restaurant for a given time
 */
public class RegisterRestaurantScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;
    private Customer customer;

    /**
     * Constructs scene for restaurant registration
     * @param clientController
     * @param application
     */
    public RegisterRestaurantScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.clientController = clientController;
        this.application = application;
        customer = new Customer();
        page1();
    }

    /**
     * Shows the first pane of restaurant times
     */
    private void page1() {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(5);

        Label emailLabel = new Label("E-Mail");
        emailLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(emailLabel, 0, 0);
        TextField emailField = new TextField(customer.getCustomerEmail());
        emailField.setPromptText("E-Mail");
        GridPane.setConstraints(emailField, 1, 0);
        Label emptyEmail = new Label("E-Mail cannot be empty");
        emptyEmail.setFont(new Font("Arial", 20));
        emptyEmail.setTextFill(Color.RED);
        GridPane.setConstraints(emptyEmail, 1, 1);
        emptyEmail.setVisible(false);
        Label takenEmail = new Label("E-Mail is already registered");
        takenEmail.setFont(new Font("Arial", 20));
        takenEmail.setTextFill(Color.RED);
        GridPane.setConstraints(takenEmail, 1, 1);
        takenEmail.setVisible(false);

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

        Button nextButton = new Button("Next");
        nextButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                emptyEmail.setVisible(false);
                emptyPassword.setVisible(false);
                takenEmail.setVisible(false);
                if (emailField.getText() != null && passwordField.getText() != null) {
                    String email = emailField.getText().trim();
                    String password = passwordField.getText();
                    if (email.isEmpty() || password.isEmpty()) {
                        if (email.isEmpty()) {
                            emptyEmail.setVisible(true);
                        }
                        if (password.isEmpty()) {
                            emptyPassword.setVisible(true);
                        }
                    } else {
                        try {
                            clientController.getCustomerByEmail(email);
                            takenEmail.setVisible(true);
                        } catch (RuntimeException emailException) {
                            String encodedPassword = application.getArgon2PasswordEncoder().encode(password);
                            customer = new Customer("username", email, encodedPassword);
                            page2();
                        }
                    }
                }
            }
        });
        grid.getChildren().addAll(emailLabel, emailField, emptyEmail, takenEmail, passwordLabel, passwordField, emptyPassword, nextButton);
        grid.setAlignment(Pos.CENTER);
        draw(grid, nextButton, application.createBackButton());
    }

    /**
     * Via pagination, shows next page of restaurant timeslots
     */
    private void page2() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(5);

        Label nameLabel = new Label("Restaurant Name:");
        nameLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameField = new TextField();
        nameField.setPromptText("Restaurant Name");
        GridPane.setConstraints(nameField, 1, 0);
        Label emptyName = new Label("Restaurant Name cannot be empty");
        emptyName.setFont(new Font("Arial", 20));
        emptyName.setTextFill(Color.RED);
        GridPane.setConstraints(emptyName, 1, 1);
        emptyName.setVisible(false);

        Label addressLabel = new Label("Restaurant Address:");
        addressLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(addressLabel, 0, 2);
        TextField addressField = new TextField();
        addressField.setPromptText("Restaurant Address");
        GridPane.setConstraints(addressField, 1, 2);
        Label emptyAddress = new Label("Restaurant Address cannot be empty");
        emptyAddress.setFont(new Font("Arial", 20));
        emptyAddress.setTextFill(Color.RED);
        GridPane.setConstraints(emptyAddress, 1, 3);
        emptyAddress.setVisible(false);

        Label genreLabel = new Label("Genre:");
        genreLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(genreLabel, 0, 4);
        ChoiceBox<String> genre = new ChoiceBox<>();
        List<String> genreList =
                Arrays.stream(Genre.values())
                        .map(g -> g.toString().replace('_', ' '))
                        .map(g -> g.charAt(0) + g.substring(1).toLowerCase())
                        .toList();
        genre.getItems().addAll(genreList);
        GridPane.setConstraints(genre, 1, 4);
        Label emptyGenre = new Label("Price Category cannot be empty");
        emptyGenre.setFont(new Font("Arial", 20));
        emptyGenre.setTextFill(Color.RED);
        GridPane.setConstraints(emptyGenre, 1, 5);
        emptyGenre.setVisible(false);

        Label priceCategoryLabel = new Label("Price Category:");
        priceCategoryLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(priceCategoryLabel, 0, 6);
        ChoiceBox<String> priceCategory = new ChoiceBox<>();
        List<String> categories =
                Arrays.stream(PriceCategory.values())
                        .map(p -> p.toString().replace('_', ' '))
                        .map(String::toLowerCase)
                        .toList();
        priceCategory.getItems().addAll(categories);
        GridPane.setConstraints(priceCategory, 1, 6);
        Label emptyPriceCategory = new Label("Price Category cannot be empty");
        emptyPriceCategory.setFont(new Font("Arial", 20));
        emptyPriceCategory.setTextFill(Color.RED);
        GridPane.setConstraints(emptyPriceCategory, 1, 7);
        emptyPriceCategory.setVisible(false);

        Label ratingLabel = new Label("Average Rating:");
        ratingLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(ratingLabel, 0, 8);
        TextField ratingField = new TextField();
        ratingField.setPromptText("Restaurant Rating");
        GridPane.setConstraints(ratingField, 1, 8);
        Label emptyRating = new Label("Restaurant Rating cannot be empty");
        emptyRating.setFont(new Font("Arial", 20));
        emptyRating.setTextFill(Color.RED);
        GridPane.setConstraints(emptyRating, 1, 9);
        emptyRating.setVisible(false);

        Label numOfTablesLabel = new Label("Number of Tables:");
        numOfTablesLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(numOfTablesLabel, 0, 10);
        TextField numOfTablesField = new TextField();
        numOfTablesField.setPromptText("Tables");
        GridPane.setConstraints(numOfTablesField, 1, 10);
        Label emptyNumOfTables = new Label("Number of Tables cannot be empty");
        emptyNumOfTables.setFont(new Font("Arial", 20));
        emptyNumOfTables.setTextFill(Color.RED);
        GridPane.setConstraints(emptyNumOfTables, 1, 11);
        emptyNumOfTables.setVisible(false);

        Label openingTimeLabel = new Label("Opening Time:");
        openingTimeLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(openingTimeLabel, 0, 12);
        TextField openingTimeField = new TextField();
        openingTimeField.setPromptText("Opening Time");
        GridPane.setConstraints(openingTimeField, 1, 12);
        Label emptyOpeningTime = new Label("Opening Time cannot be empty");
        emptyOpeningTime.setFont(new Font("Arial", 20));
        emptyOpeningTime.setTextFill(Color.RED);
        GridPane.setConstraints(emptyOpeningTime, 1, 13);
        emptyOpeningTime.setVisible(false);

        Label closingTimeLabel = new Label("Closing Time:");
        closingTimeLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(closingTimeLabel, 0, 14);
        TextField closingTimeField = new TextField();
        closingTimeField.setPromptText("Closing Time");
        GridPane.setConstraints(closingTimeField, 1, 14);
        Label emptyClosingTime = new Label("Closing Time cannot be empty");
        emptyClosingTime.setFont(new Font("Arial", 20));
        emptyClosingTime.setTextFill(Color.RED);
        GridPane.setConstraints(emptyClosingTime, 1, 15);
        emptyClosingTime.setVisible(false);

        Button registerButton = new Button("Register");
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                return;
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                page1();
            }
        });

        grid.getChildren().addAll(nameLabel, nameField, emptyName, addressLabel, addressField, emptyAddress,
                genreLabel, genre, emptyGenre, priceCategoryLabel, priceCategory, emptyPriceCategory,
                ratingLabel, ratingField, emptyRating, numOfTablesLabel, numOfTablesField, emptyNumOfTables,
                openingTimeLabel, openingTimeField, emptyOpeningTime, closingTimeLabel, closingTimeField, emptyClosingTime);
        grid.setAlignment(Pos.CENTER);

        draw(grid, registerButton, backButton);
    }

    /**
     * Draws Restaurant image
     * @param grid
     * @param registerButton
     * @param backButton
     */
    private void draw(GridPane grid, Button registerButton, Button backButton) {
        Label headline = new Label("Register your Restaurant");
        headline.setFont(new Font("Arial", 20));
        var vBox = new VBox(10, headline, grid, registerButton, backButton);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
