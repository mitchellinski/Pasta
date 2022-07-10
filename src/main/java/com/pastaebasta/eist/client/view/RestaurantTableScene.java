package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;

public class RestaurantTableScene extends Scene {
    private static final String[] RESTAURANT_MAPS = new String[]{
            ///src/main/resources/
            "Restaurant_1.png", //5
            "Restaurant_2.png", //22
            "Restaurant_3.png", //22
            "Restaurant_4.png", //16
            "Restaurant_5.png", //8
            "Restaurant_6.png", //12
            "Restaurant_7.png", //12
            "Restaurant_8.png", //20
    };
    private static final int[] RESTAURANT_TABLE_NUM = new int[]{
            5,
            22,
            22,
            16,
            8,
            12,
            12,
            20
    };
    private final ClientController clientController;
    private final ViewApplication application;
    private GridPane grid;

    public RestaurantTableScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getWidth());
        this.clientController = clientController;
        this.application = application;
        try {
            init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void init() throws FileNotFoundException {
        // TEST
        application.setRestaurant(Restaurant.builder().restaurantId("restaurantId_1").build());


        Restaurant currentRestaurant = application.getRestaurant();
        if (currentRestaurant == null) {
            return;
        }
        byte[] bytes = currentRestaurant.getRestaurantId().getBytes();
        int index = Math.abs(bytes[0] % RESTAURANT_MAPS.length);
        URL restaurantPathUrl = getClass().getClassLoader().getResource(RESTAURANT_MAPS[index]);
        if (restaurantPathUrl == null) {
            throw new IllegalArgumentException(
                "Please ensure that your resources folder contains the appropriate files for this exercise."
            );
        }
        Image image = new Image(((URL) restaurantPathUrl).toExternalForm());

//        Image image = new Image(inputStream);
//        GridPane.setColumnIndex(new ImageView(image, 0, 0), 0);
        grid = new GridPane();
        grid.add(new ImageView(image), 0, 0);

        for (int i = 0; i < RESTAURANT_TABLE_NUM[index]; i++) {
            Button button = new Button("Table " + i + 1);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
//                    String customerId = application.getCustomer().getCustomerId();
//                    Reservation newReservation = new Reservation();
//                                       clientController.addReservation();
                }
            });
            GridPane.setConstraints(button, 2, i);
            grid.getChildren().add(button);
        }

        var vBox = new VBox(10, grid);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
