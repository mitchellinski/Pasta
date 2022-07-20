package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


public class RestaurantScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;
    private final Restaurant restaurant;

    public RestaurantScene(ClientController clientController, ViewApplication application, Restaurant restaurant) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.clientController = clientController;
        this.application = application;
        this.restaurant = restaurant;
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        Label headline = new Label(restaurant.getName());
        headline.setFont(new Font("Arial", 20));

        Label openHours = new Label("Open Hours: " + '\n'
                + "Monday - Sunday: "+restaurant.getOpeningTime() + "am - " +restaurant.getClosingTime() + "pm");
        openHours.setFont(new Font("Arial", 20));
        GridPane.setConstraints(openHours, 0, 0);

        Button reservationButton = new Button("Reserve a table");
        reservationButton.setOnAction(event -> application.showRestaurantTableScene(restaurant, false));
        GridPane.setConstraints(reservationButton, 0, 1);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> application.showSearchScene());
        backButton.setAlignment(Pos.BASELINE_CENTER);

        grid.getChildren().addAll(openHours, reservationButton);

        var vBox = new VBox(10, headline, openHours, reservationButton, backButton);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
