package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.client.helper.EventSchedulingService;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantTableScene extends Scene {

    private static final int MAXIMUM_AMOUNT_OF_VISITORS = 4;
    private static final String GOOGLE_WARNING_PATH_IMAGE = "warning.png";

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
        settingsPopup();
        if (eventSchedulingService == null) {
            googlePopout();
        }
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
                "Missing files at resources folder"
            );
        }
        Image image = new Image(restaurantPathUrl.toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setX(application.getStage().getScene().getX());
        imageView.setY(application.getStage().getScene().getY());
        imageView.setPreserveRatio(true);
        GridPane.setConstraints(imageView, 0, 0);
        GridPane.setRowSpan(imageView, 11);
        grid.getChildren().add(imageView);
        grid.setGridLinesVisible(false);

        ArrayList<Reservation> reservations = clientController.getRestaurantReservations(restaurant.getRestaurantId(), startTime, startTime.plusHours(1));
        System.out.println("RES:" + reservations);
        for (int i = 0; i < RESTAURANT_TABLE_NUM[index]; i++) {
            int tableId = i + 1;
            Button button = new Button("Table " + tableId);
            button.setOnAction(event -> confirmationPopup(button.getText(), tableId));
            button.setAlignment(Pos.CENTER);
            GridPane.setConstraints(button, (i / 10) + 1, i % 10);
            grid.getChildren().add(button);

            if (reservations.stream().anyMatch(x->x.getTableId() == tableId)) {
                button.setDisable(true);
            }
        }
        var vBox = new VBox(10, headline, grid, application.createBackButton());
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
                this.eventSchedulingService = new EventSchedulingService();
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
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

    private void settingsPopup() {
        Popup popup = new Popup();
        System.out.println(restaurant);

        Label visitorLabel = new Label("How many visitors?");
        visitorLabel.setFont(new Font("Arial", 20));

        final ChoiceBox<Integer>[] visitors = new ChoiceBox[]{new ChoiceBox<>()};
        for (int i = 0; i < MAXIMUM_AMOUNT_OF_VISITORS; i++) {
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
            popup.hide();

            if (backToSearch) {
                application.showSearchScene(null);
                return;
            }

            application.showRestaurantScene(restaurant);
        });

        HBox buttonBox = new HBox(10, reservationButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, visitorLabel, visitors[0], addVisitors, dateLabel, datePicker, addDate, timeSlot, timeSlotsBox, addTimeSlot, buttonBox);
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

    public void resultPopup(Reservation reservation) {
        Popup popup = new Popup();

        Label headline = new Label(reservation  == null ? "An error occurred!" : "Reservation successfully created");
        headline.setFont(new Font("Arial", 20));
        Text text = new Text(reservation  == null ? "Something went wrong while we were trying to create your reservation.\n" +
                "It's possible you have reached the maximum amount of reservations (3 per customer), or the table was already occupied."
                : String.format("Successfully created reservation for %s, on %s. Table: %d. Visitors: %d.\nAddress: %s\nImportant: Please remember to confirm or cancel your reservation up to 12 hours before it begins in the app",
                reservation.getRestaurantName(),
                reservation.getStartDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' HH:mm")),
                reservation.getTableId(),
                reservation.getNumberOfVisitors(),
                restaurant.getAddress()
        ));
        text.setVisible(true);
        text.setFont(new Font("Arial", 12));

        Button cancelButton = new Button("Close");
        cancelButton.setOnAction(event -> popup.hide());

        HBox buttonBox = new HBox(10, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline,text, buttonBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), null)));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
        vBox.setPrefWidth(600);
        vBox.setPrefHeight(150);
        vBox.setSpacing(50);
        vBox.setPadding(new Insets(5));

        popup.getContent().add(vBox);
        popup.show(application.getStage());
        popup.centerOnScreen();
    }

    public void confirmationPopup(String tableName, int tableId) {
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
        Label restaurantValue = new Label(restaurant.getName() + " " + restaurant.getRatingAverage() + " / 5.0");
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
            popup.hide();
            String customerId = application.getCustomer().getCustomerId();
            String restaurantId = restaurant.getRestaurantId();
            LocalDateTime endDateTime = startTime.plusHours(1);
            long tableId_Long = Integer.toUnsignedLong(tableId);
            Reservation newReservation = Reservation.builder()
                .restaurantId(restaurantId)
                .tableId(tableId_Long)
                .customerId(customerId)
                .startDateTime(startTime)
                .numberOfVisitors(numOfVisitors)
                .endDateTime(endDateTime)
                .restaurantName(restaurant.getName())
                .build();
            Reservation persistedReservation = null;
            try {
                persistedReservation = clientController.addReservation(newReservation);

            } catch (Exception e) {
                e.printStackTrace();
            }
            resultPopup(persistedReservation);
            if (persistedReservation!=null) {
                try {
                    eventSchedulingService.createEvent(
                        "Reservation at " + restaurant.getName(),
                        restaurant.getAddress(),
                        "Table: " + tableId + "\nVisitors: "+ numOfVisitors + "\nImportant: Please remember to confirm your reservation up to 12 hours before it begins in the app or by clicking http://localhost:8080/api/v1/reservations/"+persistedReservation.getReservationId()+"/confirm",
                        persistedReservation.getStartDateTime(),
                        persistedReservation.getEndDateTime()
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            application.showSearchScene(null);
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> popup.hide());

        HBox buttonBox = new HBox(10, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline, grid, buttonBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), null)));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
        vBox.setPrefWidth(600);
        vBox.setPrefHeight(150);
        vBox.setSpacing(50);
        vBox.setPadding(new Insets(5));

        popup.getContent().add(vBox);
        popup.show(application.getStage());
        popup.centerOnScreen();
    }

}
