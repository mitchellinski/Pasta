package com.pastaebasta.eist.client.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;

public class HomeScene extends Scene {

    private final ViewApplication application;

    public HomeScene(ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.application = application;
        var vBox = new VBox(10);

        Label headline = new Label("Pasta e Basta");
        Label subtitle = new Label("Welcome!");
        subtitle.setFont(new Font("Arial", 25));
        subtitle.setAlignment(Pos.TOP_CENTER);
        headline.setFont(new Font("Arial", 50));
        headline.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(headline,subtitle);

        if (!application.isLoggedIn()) {
            Button logInButton = new Button("Log in");
            logInButton.setOnAction(event -> application.showLogInScene());
            Button registerButton = new Button("New user");
            registerButton.setOnAction(event -> application.showRegisterScene());
            Label warning = new Label("Please log-in or create a \nnew user to access reservations.");
            warning.setFont(new Font("Arial", 12));
            warning.setTextAlignment(TextAlignment.CENTER);
            warning.setVisible(true);
            vBox.getChildren().addAll(logInButton, registerButton, warning);
        }

        if (application.isLoggedIn()) {
            Button searchButton = new Button("Search for Restaurants");
            searchButton.setOnAction(event -> application.showSearchScene());
            vBox.getChildren().add(searchButton);

            Button reservationsButton = new Button("Show past Reservations");
            reservationsButton.setOnAction(event -> application.showPastReservationsScene());
            vBox.getChildren().add(reservationsButton);

            Button profileButton = new Button("My Profile");
            profileButton.setOnAction(event -> application.showProfileScene());
            vBox.getChildren().add(profileButton);

            Button logOutButton = new Button("Log Out");
            logOutButton.setOnAction(event -> createPopup());
            vBox.getChildren().add(logOutButton);
        }
        /*
        //START TESTING!!!!!
        Genre genre = Genre.AMERICAN;
        PriceCategory priceCategory = PriceCategory.VERY_EXPENSIVE;
        Restaurant testRestaurant = Restaurant.builder().restaurantId("restaurantId_1").name("Test Name").address("Test Address").restaurantGenre(genre).coordinates("0, 0").priceCategory(priceCategory)
                .ratingAverage(5.0).numberOfTables(3).openingTime(8).closingTime(22).build();
        //END TESTING!!!!

        Button tables = new Button("Tables");
        tables.setOnAction(event -> application.showRestaurantTableScene(testRestaurant, false));
        vBox.getChildren().add(tables);

        Button restaurant = new Button("Restaurant");
        restaurant.setOnAction(event -> application.showRestaurantScene(testRestaurant));
        vBox.getChildren().add(restaurant);
         */



        Button registerRestaurant = new Button("Restaurant Registration");
        registerRestaurant.setOnAction(event -> application.showRegisterRestaurantScene());
        vBox.getChildren().add(registerRestaurant);

        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private void createPopup() {
        Popup popup = new Popup();

        Label confirmationMessage = new Label("Are you sure you want to log out?");
        confirmationMessage.setFont(new Font("Arial", 20));

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(event -> {
            application.setCustomer(null);
            popup.hide();
            application.showHomeScene();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(event -> popup.hide());

        HBox buttonBox = new HBox(10, yesButton, noButton);
        buttonBox.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, confirmationMessage, buttonBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), null)));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
        vBox.setPrefWidth(400);
        vBox.setPrefHeight(150);
        vBox.setSpacing(50);
        vBox.setPadding(new Insets(5));

        popup.getContent().add(vBox);
        popup.show(application.getStage());
        popup.centerOnScreen();
    }
}
