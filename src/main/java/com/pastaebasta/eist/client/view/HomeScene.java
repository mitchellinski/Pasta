package com.pastaebasta.eist.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;

public class HomeScene extends Scene {

    private final ViewApplication application;

    public HomeScene(ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.application = application;
        var vBox = new VBox(10);

        Label headline = new Label("Welcome!");
        headline.setFont(new Font("Arial", 50));
        headline.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().add(headline);

        if (!application.isLoggedIn()) {
        Button logInButton = new Button("Log in");
        logInButton.setOnAction(event -> application.showLogInScene());
            vBox.getChildren().add(logInButton);
        }
        Button searchButton = new Button("Search for Restaurants");
        searchButton.setOnAction(event -> application.showSearchScene());
      vBox.getChildren().add(searchButton);

        if (application.isLoggedIn()) {
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

        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private void createPopup() {
        Popup popup = new Popup();

        Label confirmationMessage = new Label("Are you sure you want to log out?");
        confirmationMessage.setFont(new Font("Arial", 20));

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                application.setCustomer(null);
                popup.hide();
                application.showHomeScene();
            }
        });

        Button noButton = new Button("No");
        noButton.setOnAction(event -> popup.hide());

        HBox buttonBox = new HBox(10, yesButton, noButton);
        buttonBox.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, confirmationMessage, buttonBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        vBox.setPrefWidth(400);
        vBox.setPrefHeight(150);
        vBox.setSpacing(50);
        vBox.setPadding(new Insets(5));

        popup.getContent().add(vBox);
        popup.show(application.getStage());
        popup.centerOnScreen();
    }
}
