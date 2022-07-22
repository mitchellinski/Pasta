package com.pastaebasta.eist.client.view;

import com.google.maps.model.Photo;
import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.client.helper.EventSchedulingService;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.IOException;
import java.security.GeneralSecurityException;


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

        Photo[] photos = restaurant.getPictures();
        Photo profilePhoto;
        if(photos.length > 0) {
            profilePhoto = photos[0];
        }
 //       Image image = new Image(profilePhoto.photoReference);

        Label addressLabel = new Label("Address: ");
        addressLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(addressLabel, 0, 0);
        Label addressValue = new Label(restaurant.getAddress());
        addressValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(addressValue, 1, 0);

        Label ratingLabel = new Label("Rating: ");
        ratingLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(ratingLabel, 0, 1);
        Label ratingValue = new Label(String.valueOf(restaurant.getRatingAverage()));
        ratingValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(ratingValue, 1, 1);

        Label openLabel = new Label("Currently Open: ");
        openLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(openLabel, 0, 2);
        Label openValue = new Label(restaurant.getOpeningHours().openNow.toString());
        openValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(openValue, 1, 2);

        Button reservationButton = new Button("Reserve a table");
        reservationButton.setOnAction(event -> application.showRestaurantTableScene(restaurant, false));

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> application.showSearchScene());
        backButton.setAlignment(Pos.BASELINE_CENTER);

        grid.getChildren().addAll(addressLabel, addressValue, ratingLabel, ratingValue, openLabel, openValue);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid, reservationButton, backButton);
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
