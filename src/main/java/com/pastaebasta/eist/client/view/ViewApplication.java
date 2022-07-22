package com.pastaebasta.eist.client.view;

import com.google.maps.model.LatLng;
import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.client.helper.EventSchedulingService;
import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.io.IOException;

/**
 * Application
 * JavaFX Application wrapper
 *
 * Serves as a unifying service for the client View.
 * It can be used to access the controllers, and thus gain access to the server.
 *
 * It is also used to setore information about the current customer, their log-in data,
 * address, and calendar.
 *
 * @author Michael
 */
@Getter
@Setter
public class ViewApplication extends Application {

    private final ClientController clientController = new ClientController();
    private Stage stage;
    private Customer customer;
    private boolean loggedIn;
    private Restaurant restaurant;
    private EventSchedulingService eventSchedulingService;
    private LatLng latLng;

    private boolean googleAuthorized;

    private final Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();

    /**
     * Sets up the views
     * @param primaryStage first Stage
     */
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        primaryStage.setScene(new Scene(new VBox()));
        primaryStage.setScene(new HomeScene(this));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Show the homepage
     */
    public void showHomeScene() {
        latLng = null;
        stage.setScene(new HomeScene(this));
    }

    /**
     * Show log-in page
     */
    public void showLogInScene() {
        stage.setScene(new LogInScene(clientController, this));
    }

    /**
     * Show past reservations page
     */
    public void showPastReservationsScene() {
        stage.setScene(new PastReservationsScene(clientController, this));
    }

    /**
     * Shows the search page
     */
    public void showSearchScene() {
        stage.setScene(new SearchScene(clientController, this, latLng));
    }

    /**
     * Shows the embedded Google Maps page
     * @throws IOException
     */
    public void showMapsScene() throws IOException {
        stage.setScene(new MapsScene(clientController, this, latLng));
    }

    /**
     * Shows the user account registration page
     */
    public void showRegisterScene() {
        stage.setScene(new RegisterScene(clientController, this));
    }


    /**
     * Shows the user profile page
     */
    public void showProfileScene() {
        stage.setScene(new ProfileScene(clientController, this));
    }

    /**
     * Shows the settings page
     */
    public void showSettingsScene() {
        stage.setScene(new SettingsScene(clientController, this));
    }

    /**
     * Shows the interior of a restaurant
     * @param restaurant
     * @param backToSearch
     */
    public void showRestaurantTableScene(Restaurant restaurant, boolean backToSearch) {
        stage.setScene(new RestaurantTableScene(clientController, this, restaurant, backToSearch));
    }

    /**
     * Shows you details about the restaurant
     * @param restaurant
     */
    public void showRestaurantScene(Restaurant restaurant) {
        stage.setScene(new RestaurantScene(clientController, this, restaurant));
    }

    /**
     * Sets a new customer and updates the loggedIn field
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
        loggedIn = customer != null;
    }


    /**
     * Creates a return button
     * @return Button
     */
    Button createBackButton() {
        Button backButton = new Button("Home");
        backButton.setOnAction(event -> showHomeScene());
        backButton.setAlignment(Pos.BASELINE_CENTER);
        return backButton;
    }
}
