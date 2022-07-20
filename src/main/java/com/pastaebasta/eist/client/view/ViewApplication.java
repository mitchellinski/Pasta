package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.time.LocalDateTime;

public class ViewApplication extends Application {

    private final ClientController clientController = new ClientController();
    private Stage stage;
    private Customer customer;
    private boolean loggedIn;
    private Restaurant restaurant;

    private final Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        primaryStage.setScene(new Scene(new VBox()));
        primaryStage.setScene(new HomeScene(this));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public void showHomeScene() {
        stage.setScene(new HomeScene(this));
    }

    public void showLogInScene() {
        stage.setScene(new LogInScene(clientController, this));
    }

    public void showPastReservationsScene() {
        stage.setScene(new PastReservationsScene(clientController, this));
    }

    public void showSearchScene() {
        stage.setScene(new SearchScene(clientController, this));
    }

    public void showRegisterScene() {
        stage.setScene(new RegisterScene(clientController, this));
    }

    public void showProfileScene() {
        stage.setScene(new ProfileScene(clientController, this));
    }

    public void showSettingsScene() {
        stage.setScene(new SettingsScene(clientController, this));
    }

    public void showRestaurantTableScene(Restaurant restaurant, boolean backToSearch) {
        stage.setScene(new RestaurantTableScene(clientController, this, restaurant, backToSearch));
    }

    public void showRestaurantScene(Restaurant restaurant) {
        stage.setScene(new RestaurantScene(clientController, this, restaurant));
    }

    public Stage getStage() {
        return stage;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        loggedIn = customer != null;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    Button createBackButton() {
        Button backButton = new Button("Home");
        backButton.setOnAction(event -> showHomeScene());
        backButton.setAlignment(Pos.BASELINE_CENTER);
        return backButton;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Argon2PasswordEncoder getArgon2PasswordEncoder() {
        return argon2PasswordEncoder;
    }
}
