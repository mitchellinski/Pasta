package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Reservation;
import javafx.application.Platform;
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


public class PastReservationsScene extends Scene {
    private final ClientController clientController;
    private final ViewApplication application;
    private final ObservableList<Reservation> reservations;

    private final TableView<Reservation> table;

    public PastReservationsScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        int width = (int) application.getStage().getScene().getWidth();
        this.clientController = clientController;
        this.application = application;
        this.reservations = FXCollections.observableArrayList();
        clientController.getCustomerReservations(application.getCustomer().getCustomerId(), this::setReservations);
        table = new TableView<>(reservations);

        Label headline = new Label("Past Reservations");
        headline.setFont(new Font("Arial", 20));
        double w = width / 6.0;

        var buttonColumn = new TableColumn<Reservation, String>("ID");
        buttonColumn.setSortable(false);
        buttonColumn.setPrefWidth(w);
        buttonColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));

        var idColumn = new TableColumn<Reservation, String>("ID");
        idColumn.setSortable(false);
        idColumn.setPrefWidth(w);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        var dateColumn = new TableColumn<Reservation, String>("Date");
        dateColumn.setSortable(true);
        dateColumn.setPrefWidth(w);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        var nameColumn = new TableColumn<Reservation, String>("Restaurant");
        nameColumn.setSortable(true);
        nameColumn.setPrefWidth(w);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        var visitorColumn = new TableColumn<Reservation, String>("Visitors");
        visitorColumn.setSortable(true);
        visitorColumn.setPrefWidth(w);
        visitorColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfVisitors"));
        var confirmationColumn = new TableColumn<Reservation, String>("Confirmation Status");
        confirmationColumn.setSortable(true);
        confirmationColumn.setPrefWidth(w);
        confirmationColumn.setCellValueFactory(new PropertyValueFactory<>("confirmed"));
        table.getColumns().addAll(idColumn, dateColumn, nameColumn, visitorColumn, confirmationColumn);
        addButtonToTable(w);

        var vBox = new VBox(10, headline, table, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    public void editPopup(Reservation reservation) {
        Popup popup = new Popup();

        Label headline = new Label("Edit reservation");
        headline.setFont(new Font("Arial", 20));
        Text text = new Text(
            String.format("Reservation for %s, on %s. Table: %d. Visitors: %d.\nImportant: Please remember to confirm or cancel your reservation up to 12 hours before it begins in the app",
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
                reservation.setConfirmed(true);
                warning.setVisible(false);
                popup.hide();
                table.refresh();
                return;
            }

            warning.setText("You may only confirm a reservation up to twelve hours before it starts!");
            warning.setVisible(true);
        });

        Button cancelButton = new Button("Cancel reservation");
        cancelButton.setOnAction(event -> {
            if (reservation.isConfirmed()) {
                warning.setText("Your reservation starts in less than 12 hours, you may no longer cancel this reservation!");
                warning.setVisible(true);
                return;
            }

            if (LocalDateTime.now().plusHours(12).isBefore(reservation.getStartDateTime())) {
                reservation.setConfirmed(false);
                warning.setVisible(false);
                popup.hide();
                table.refresh();
                return;
            }

            warning.setText("You may only cancel a reservation up to twelve hours before it starts!");
            warning.setVisible(true);

        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            warning.setVisible(false);
            popup.hide();
            table.refresh();
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

    private void addButtonToTable(double width) {
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
        table.getColumns().add(colBtn);
    }

    private void setReservations(List<Reservation> reservations) {
        Platform.runLater(() -> this.reservations.setAll(reservations));
    }


}
