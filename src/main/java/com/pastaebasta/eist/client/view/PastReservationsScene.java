package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Reservation;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Scene - Past Reservation
 *
 *
 *
 * Scene allows a user to view past reservations
 */
public class PastReservationsScene extends Scene {
    private final ClientController clientController;
    private final ViewApplication application;
    private final ObservableList<Reservation> activeReservations;
    private final ObservableList<Reservation> pastReservations;
    private TableView<Reservation> activeTable = new TableView<>();
    private TableView<Reservation> pastTable;
    double width;

    /**
     * Past Reservation Constructor
     * @param clientController
     * @param application
     */
    public PastReservationsScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        width = application.getStage().getScene().getWidth() / 7.0;
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

        activeTable = createTable(activeReservations);

        Label pastLabel = new Label("Past Reservations");
        pastLabel.setFont(new Font("Arial", 20));

        pastTable = createTable(pastReservations);

        var vBox = new VBox(10, headline, activeLabel, activeTable, pastLabel, pastTable, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    /**
     * Setter for active reservations
     * @param reservations
     */
    private void setActiveReservations(List<Reservation> reservations) {
        Platform.runLater(() -> this.activeReservations.setAll(reservations));
    }

    /**
     * Setter for Past Reservations
     * @param reservations
     */
    private void setPastReservations(List<Reservation> reservations) {
        Platform.runLater(() -> this.pastReservations.setAll(reservations));
    }

    /**
     * Creates tables based on a list of reservations
     * @param reservations
     * @return TableView of Reservations
     */
    private TableView<Reservation> createTable(ObservableList<Reservation> reservations) {
        TableView<Reservation> table = new TableView<>(reservations);

        var idColumn = new TableColumn<Reservation, String>("ID");
        idColumn.setSortable(false);
        idColumn.setPrefWidth(width);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        var dateColumn = new TableColumn<Reservation, String>("Date");
        dateColumn.setSortable(true);
        dateColumn.setPrefWidth(width);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        dateColumn.setPrefWidth(width);
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
        nameColumn.setPrefWidth(width);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        var visitorColumn = new TableColumn<Reservation, String>("Visitors");
        visitorColumn.setSortable(true);
        visitorColumn.setPrefWidth(width);
        visitorColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfVisitors"));
        var confirmationColumn = new TableColumn<Reservation, String>("Confirmation Status");
        confirmationColumn.setSortable(true);
        confirmationColumn.setPrefWidth(width);
        confirmationColumn.setCellValueFactory(new PropertyValueFactory<>("confirmed"));
        table.getColumns().addAll(idColumn, dateColumn, nameColumn, visitorColumn, confirmationColumn);
        addButtonToTable();

        return table;
    }

    /**
     * Edits time to be stylized
     * @param time
     * @return String of time
     */
    private String reformatTime(LocalDateTime time) {
        String hour = addZero(String.valueOf(time.getHour()));
        String minute = addZero(String.valueOf(time.getMinute()));
        return hour + ":" + minute;
    }

    /**
     * Adds zeros to time
     * @param time
     * @return String of time
     */
    private String addZero(String time) {
        if (time.length() < 2) {
            time = "0".concat(time);
        }
        return time;
    }

    /**
     * Creates edit popup of reservation
     * @param reservation
     */
    public void editPopup(Reservation reservation) {
        Popup popup = new Popup();

        Label headline = new Label("Edit reservation");
        headline.setFont(new Font("Arial", 20));
        Text text = new Text(
            String.format("Reservation for %s, on %s. Table: %d. Visitors: %d.\nImportant: Please remember to confirm or cancel your reservation up to 12 hours before it begins in the app. You may not cancel a reservation after you have already confirmed it!",
            reservation.getRestaurantName(),
            reservation.getStartDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' HH:mm")),
            reservation.getTableId(),
            reservation.getNumberOfVisitors()
        ));
        text.setFont(new Font("Arial", 12));
        Label warning = new Label();
        warning.setFont(new Font("Arial", 12));
        warning.setTextFill(Color.RED);
        warning.setVisible(false);

        Button confirmButton = new Button("Confirm reservation");
        confirmButton.setOnAction(event -> {
            if (LocalDateTime.now().plusHours(12).isBefore(reservation.getStartDateTime())) {
                clientController.confirmReservation(reservation);
                reservation.setConfirmed(true);
                warning.setVisible(false);
                popup.hide();
                activeTable.refresh();
                return;
            }

            warning.setText("You may only confirm a reservation up to twelve hours before it starts!");
            warning.setVisible(true);
        });

        Button cancelButton = new Button("Cancel reservation");
        cancelButton.setOnAction(event -> {
            if (reservation.isConfirmed()) {
                warning.setText("You may not cancel a reservation after you have already confirmed it!");
                warning.setVisible(true);
                return;
            }

            if (LocalDateTime.now().plusHours(12).isBefore(reservation.getStartDateTime())) {
                clientController.cancelReservation(reservation);
                reservation.setConfirmed(false);
                warning.setVisible(false);
                popup.hide();
                activeTable.refresh();
                return;
            }

            warning.setText("You may only cancel a reservation up to twelve hours before it starts!");
            warning.setVisible(true);

        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            warning.setVisible(false);
            popup.hide();
            activeTable.refresh();
        });

        HBox buttonBox = new HBox(10, confirmButton, cancelButton,closeButton );
        buttonBox.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, headline,text, warning,buttonBox);
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


    /**
     * Adds button to table list dynamically based on number of tables in restaurant
     */
    private void addButtonToTable() {
        TableColumn<Reservation, Void> colBtn = new TableColumn("Reservation");
        colBtn.setPrefWidth(width);

        Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Reservation, Void> call(final TableColumn<Reservation, Void> param) {
                final TableCell<Reservation, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Edit");
                    {
                        btn.setPrefWidth(width);
                        btn.setOnAction((ActionEvent event) -> editPopup(getTableView().getItems().get(getIndex())));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        activeTable.getColumns().add(colBtn);
    }


}
