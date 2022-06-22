package com.pastaebasta.eist.view;

import com.pastaebasta.eist.controller.ClientController;
import com.pastaebasta.eist.controller.ServerController;
import com.pastaebasta.eist.model.Customer;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ViewApplication extends Application {

    private final ClientController clientController = new ClientController();
    private Stage stage;
    private Customer customer;
    private boolean loggedIn;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.setScene(new HomeScene(this));
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

    public Stage getStage() {
        return stage;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        if (customer != null) {
            loggedIn = true;
        } else {
            loggedIn = false;
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    Button createBackButton() {
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> showHomeScene());
        return backButton;
    }
}
