package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.shared.model.Table;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.List;


public class PastReservationsScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;
    private int width;
    private final ObservableList<Reservation> activeReservations;
    private final ObservableList<Reservation> pastReservations;

    public PastReservationsScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        width = (int) application.getStage().getScene().getWidth();
        this.clientController = clientController;
        this.application = application;
        this.activeReservations = FXCollections.observableArrayList();
        this.pastReservations = FXCollections.observableArrayList();

        clientController.getCustomerReservations(application.getCustomer().getCustomerId(), this::setActiveReservations, true);
        clientController.getCustomerReservations(application.getCustomer().getCustomerId(), this::setPastReservations, false);

        Label headline = new Label("My Reservations");
        headline.setFont(new Font("Arial", 20));

        Label activeLabel = new Label("Active Reservations");
        activeLabel.setFont(new Font("Arial", 20));

        TableView<Reservation> activeTable = createTable(activeReservations);

        Label pastLabel = new Label("Past Reservations");
        pastLabel.setFont(new Font("Arial", 20));

        TableView<Reservation> pastTable = createTable(pastReservations);

        var vBox = new VBox(10, headline, activeLabel, activeTable, pastLabel, pastTable, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private void setActiveReservations(List<Reservation> reservations) {
        Platform.runLater(() -> this.activeReservations.setAll(reservations));
    }

    private void setPastReservations(List<Reservation> reservations) {
        Platform.runLater(() -> this.pastReservations.setAll(reservations));
    }

    private TableView<Reservation> createTable(ObservableList<Reservation> reservations) {
        TableView<Reservation> table = new TableView<>(reservations);

        var idColumn = new TableColumn<Reservation, String>("ID");
        idColumn.setSortable(false);
        idColumn.setPrefWidth(width / 5);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        var dateColumn = new TableColumn<Reservation, String>("Date");
        dateColumn.setSortable(true);
        dateColumn.setPrefWidth(width / 5);
        dateColumn.setCellValueFactory(param -> {
            LocalDateTime time = param.getValue().getStartDateTime();
            String day = addZero(String.valueOf(time.getDayOfMonth()));
            String month = addZero(String.valueOf(time.getMonthValue()));
            String year = String.valueOf(time.getYear());
            String startTime = reformatTime(param.getValue().getStartDateTime());
            String endTime = reformatTime(param.getValue().getEndDateTime());
            String formattedDate = day + "." + month + "." + year + " " + startTime + " - " + endTime;
            return new ReadOnlyStringWrapper(formattedDate);
        });

        var nameColumn = new TableColumn<Reservation, String>("Restaurant");
        nameColumn.setSortable(true);
        nameColumn.setPrefWidth(width / 5);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        var visitorColumn = new TableColumn<Reservation, String>("Visitors");
        visitorColumn.setSortable(true);
        visitorColumn.setPrefWidth(width / 5);
        visitorColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfVisitors"));
        var confirmedColumn = new TableColumn<Reservation, String>("Confirmed");
        confirmedColumn.setSortable(true);
        confirmedColumn.setPrefWidth(width / 5);
        confirmedColumn.setCellValueFactory(new PropertyValueFactory<>("confirmationStatus"));

        table.getColumns().addAll(idColumn, dateColumn, nameColumn, visitorColumn, confirmedColumn);

        return table;
    }

    private String reformatTime(LocalDateTime time) {
        String hour = addZero(String.valueOf(time.getHour()));
        String minute = addZero(String.valueOf(time.getMinute()));
        return hour + ":" + minute;
    }

    private String addZero(String time) {
        if (time.length() < 2) {
            time = "0".concat(time);
        }
        return time;
    }
}
