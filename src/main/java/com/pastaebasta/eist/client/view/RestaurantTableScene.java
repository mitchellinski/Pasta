package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.client.helper.EventSchedulingService;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private final Restaurant restaurant;
    private LocalDateTime startTime;
    private int numOfVisitors;
    private final boolean backToSearch;

    private EventSchedulingService eventSchedulingService;

    public RestaurantTableScene(ClientController clientController, ViewApplication application, Restaurant restaurant, boolean backToSearch) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.clientController = clientController;
        this.application = application;
        this.restaurant = restaurant;
        this.backToSearch = backToSearch;
        try {
            this.eventSchedulingService = new EventSchedulingService();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        settingsPopup();
    }

    private void init() throws FileNotFoundException {

        Label headline = new Label("Choose a Table");
        headline.setFont(new Font("Arial", 20));

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setPrefWidth(application.getStage().getScene().getWidth() / 3.0);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(20);
        // TEST

        //restaurant = Restaurant.builder().restaurantId("restaurantId_1").build();

        if (restaurant == null) {
            return;
        }
        byte[] bytes = restaurant.getRestaurantId().getBytes();
        int index = Math.abs(bytes[0] % RESTAURANT_MAPS.length);
        URL restaurantPathUrl = getClass().getClassLoader().getResource(RESTAURANT_MAPS[index]);
        if (restaurantPathUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise."
            );
        }
        Image image = new Image(((URL) restaurantPathUrl).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setX(application.getStage().getScene().getX());
        imageView.setY(application.getStage().getScene().getY());
        imageView.setPreserveRatio(true);
        GridPane.setConstraints(imageView, 0, 0);
        GridPane.setRowSpan(imageView, 11);
        grid.getChildren().add(imageView);

        for (int i = 0; i < RESTAURANT_TABLE_NUM[index]; i++) {
            Button button = new Button("Table " + (i + 1));
            button.setOnAction(event -> confirmationPopup(button.getText()));
            button.setAlignment(Pos.CENTER);
            GridPane.setConstraints(button, (i / 10) + 1, i % 10);
            grid.getChildren().add(button);
        }
        var vBox = new VBox(10, headline, grid, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private void settingsPopup() {
        Popup popup = new Popup();

        Label visitorLabel = new Label("How many visitors?");
        visitorLabel.setFont(new Font("Arial", 20));

        final ChoiceBox<Integer>[] visitors = new ChoiceBox[]{new ChoiceBox<>()};
        for (int i = 0; i < restaurant.getNumberOfTables() * 4; i++) {
            visitors[0].getItems().add(i + 1);
        }

        Label addVisitors = new Label("You have to select the number of visitors");
        addVisitors.setFont(new Font("Arial", 20));
        addVisitors.setTextFill(Color.RED);
        addVisitors.setVisible(false);

        Label dateLabel = new Label("Choose a date");
        dateLabel.setFont(new Font("Arial", 20));

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setPromptText("dd.mm.yyyy");
        datePicker.setDayCellFactory(date -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(LocalDate.now()));
            }
        });

        Label addDate = new Label("You have to select a date");
        addDate.setFont(new Font("Arial", 20));
        addDate.setTextFill(Color.RED);
        addDate.setVisible(false);

        Label timeSlot = new Label("Choose a time slot");
        timeSlot.setFont(new Font("Arial", 20));

        ChoiceBox<String> timeSlotsBox = new ChoiceBox<>();
        String[] timeSlots = getTimeSlots(restaurant);
        timeSlotsBox.getItems().addAll(timeSlots);

        Label addTimeSlot = new Label("You have to select a time slot");
        addTimeSlot.setFont(new Font("Arial", 20));
        addTimeSlot.setTextFill(Color.RED);
        addTimeSlot.setVisible(false);

        Button reservationButton = new Button("Continue to reservation");
        reservationButton.setOnAction(event -> {
            addVisitors.setVisible(false);
            addDate.setVisible(false);
            addTimeSlot.setVisible(false);

            LocalDate date = datePicker.getValue();
            String selectedTimeSlot = timeSlotsBox.getValue();

            if (visitors[0].getValue() == null || date == null || selectedTimeSlot == null) {
                if (visitors[0].getValue() == null) {
                    addVisitors.setVisible(true);
                }
                if (date == null) {
                    addDate.setVisible(true);
                }
                if (selectedTimeSlot == null) {
                    addTimeSlot.setVisible(true);
                }
            } else {
                int hour = Integer.parseInt(selectedTimeSlot.split(":")[0]);
                LocalTime time = LocalTime.of(hour, 0);
                startTime = LocalDateTime.of(date, time);
                numOfVisitors = visitors[0].getValue();
                popup.hide();
                try {
                    init();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            if (backToSearch) {
                popup.hide();
                application.showSearchScene();
            } else {
                popup.hide();
                application.showRestaurantScene(restaurant);
            }
        });

        HBox buttonBox = new HBox(10, reservationButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, visitorLabel, visitors[0], addVisitors, dateLabel, datePicker, addDate, timeSlot, timeSlotsBox, addTimeSlot, buttonBox);
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

    private String[] getTimeSlots(Restaurant restaurant) {
        String[] timeSlots = new String[restaurant.getClosingTime() - restaurant.getOpeningTime()];
        int time = restaurant.getOpeningTime();
        String timeString1;
        String timeString2;
        for (int i = 0; i < timeSlots.length; i++) {
            timeString1 = String.valueOf(time);
            if (time < 10) {
                timeString1 = "0".concat(timeString1);
            }
            timeString2 = String.valueOf(++time);
            if (time < 10) {
                timeString2 = "0".concat(timeString2);
            }
            timeSlots[i] = timeString1 + ":00 - " + timeString2 + ":00";
        }
        return timeSlots;
    }

    public void confirmationPopup(String tableName) {
        Popup popup = new Popup();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label headline = new Label("Confirm details");
        headline.setFont(new Font("Arial", 20));

        Label restaurantLabel = new Label("Restaurant:");
        restaurantLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(restaurantLabel, 0, 0);
        Label restaurantValue = new Label(restaurant.getName());
        restaurantValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(restaurantValue, 1, 0);

        String date = startTime.getDayOfMonth() + "." + startTime.getMonthValue() + "." + startTime.getYear();
        Label dateLabel = new Label("Date:");
        dateLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(dateLabel, 0, 1);
        Label dateValue = new Label(date);
        dateValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(dateValue, 1, 1);

        String minute = String.valueOf(startTime.getMinute());
        if (minute.length() < 2) {
            minute = "0".concat(minute);
        }
        String timeSlot = startTime.getHour() + ":" + minute + " - " + (startTime.getHour() + 1) + ":" + minute;
        Label timeLabel = new Label("Time slot:");
        timeLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(timeLabel, 0, 2);
        Label timeValue = new Label(timeSlot);
        timeValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(timeValue, 1, 2);

        Label visitorLabel = new Label("Number of Visitors:");
        visitorLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(visitorLabel, 0, 3);
        Label visitorValue = new Label(String.valueOf(numOfVisitors));
        visitorValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(visitorValue, 1, 3);

        Label tableLabel = new Label("Selected Table:");
        tableLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(tableLabel, 0, 4);
        Label tableValue = new Label(String.valueOf(tableName));
        tableValue.setFont(new Font("Arial", 20));
        GridPane.setConstraints(tableValue, 1, 4);

        grid.getChildren().addAll(restaurantLabel, restaurantValue, dateLabel, dateValue, timeLabel, timeValue, visitorLabel, visitorValue, tableLabel, tableValue);
        grid.setAlignment(Pos.CENTER);

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
            String customerId = application.getCustomer().getCustomerId();
            String restaurantId = restaurant.getRestaurantId();
            LocalDateTime endDateTime = startTime.plusMinutes(30);
            long tableId = -1;
            Reservation newReservation = new Reservation(customerId, restaurantId, startTime, endDateTime,
                    numOfVisitors, tableId);
            Reservation persistedReservation = clientController.addReservation(newReservation);
            try {
                eventSchedulingService.createEvent(
                        "Reservation for " + numOfVisitors + ", at " + restaurant.getName(),
                        restaurant.getAddress(),
                        "Please remember to confirm your reservation up to 12 hours before it begins at http://localhost:8080/api/v1/reservations/"+persistedReservation.getReservationId()+"/confirm",
                        LocalDateTime.now()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> popup.hide());

        HBox buttonBox = new HBox(10, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid, buttonBox);
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
