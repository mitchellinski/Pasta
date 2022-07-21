package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.client.helper.EventSchedulingService;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class RestaurantScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;
    private final Restaurant restaurant;

    public RestaurantScene(ClientController clientController, ViewApplication application, Restaurant restaurant) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.clientController = clientController;
        this.application = application;
        this.restaurant = restaurant;

        if (application.getEventSchedulingService() == null && !application.isGoogleAuthorized()) {
            googlePopout();
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        Label headline = new Label(restaurant.getName());
        headline.setFont(new Font("Arial", 20));

        Label openHours = new Label("Open Hours: " + '\n'
                + "Monday - Sunday: "+restaurant.getOpeningTime() + " - " +restaurant.getClosingTime() + "pm");
        openHours.setFont(new Font("Arial", 20));
        GridPane.setConstraints(openHours, 0, 0);

        Button reservationButton = new Button("Reserve a table");
        reservationButton.setOnAction(event -> application.showRestaurantTableScene(restaurant, false));
        GridPane.setConstraints(reservationButton, 0, 1);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> application.showSearchScene(null));
        backButton.setAlignment(Pos.BASELINE_CENTER);

        grid.getChildren().addAll(openHours, reservationButton);

        var vBox = new VBox(10, headline, openHours, reservationButton, backButton);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private void googlePopout() {
        Popup popup = new Popup();

        Label title = new Label("Warning");
        title.setFont(new Font("Arial", 20));
        Text addDate = new Text("To create reservations, you must allow the app to add events to your google calendar.\n" +
                "After clicking okay, you will be redirected to your browser. Please grant the app permissions.\n\n" +
                "You may get a warning saying the app has not yet been verified - simply click \"Advanced\", and continue.\n" +
                "Do not click \"take me back to safety\"!\n\n" +
                "If you've already done this before, you may ignore this warning.");
        addDate.setFont(new Font("Arial", 15));


        Button confirmButton = new Button("Okay");
        confirmButton.setOnAction(event -> {
            popup.hide();
            try {
                application.setEventSchedulingService(new EventSchedulingService());
                application.setGoogleAuthorized(true);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
                application.setGoogleAuthorized(false);
            }
        });

        HBox buttonBox = new HBox(10, confirmButton);
        buttonBox.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, title, addDate, buttonBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), null)));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
        vBox.setPrefWidth(400);
        vBox.setPrefHeight(200);
        vBox.setSpacing(50);
        vBox.setPadding(new Insets(5));

        popup.getContent().add(vBox);
        popup.show(application.getStage());
        popup.centerOnScreen();
    }


}
