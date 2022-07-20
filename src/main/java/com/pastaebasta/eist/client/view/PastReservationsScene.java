package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Reservation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

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

    private void addButtonToTable(double width) {
        TableColumn<Reservation, Void> colBtn = new TableColumn("Reservation");
        colBtn.setPrefWidth(width);

        Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Reservation, Void> call(final TableColumn<Reservation, Void> param) {
                final TableCell<Reservation, Void> cell = new TableCell<>() {
                    private boolean confirmed = false;
                    private final Button btn = new Button("Confirm");
                    {
                        btn.setPrefWidth(width);
                        btn.setOnAction((ActionEvent event) -> {
                            confirmed = !confirmed;
                            btn.setText(confirmed ? "Cancel" : "Confirm");
                            Reservation reservation = getTableView().getItems().get(getIndex());
                            reservation.setConfirmed(confirmed);
                            System.out.println("confirmed reservation " + reservation.getRestaurantName());
                        });
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
