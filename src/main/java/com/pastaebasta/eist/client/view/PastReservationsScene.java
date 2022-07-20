package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Reservation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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

        var idColumn = new TableColumn<Reservation, String>("ID");
        idColumn.setSortable(false);
        idColumn.setPrefWidth(width / 5);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        var dateColumn = new TableColumn<Reservation, String>("Date");
        dateColumn.setSortable(true);
        dateColumn.setPrefWidth(width / 5);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
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

        var vBox = new VBox(10, headline, table, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private void setReservations(List<Reservation> reservations) {
        Platform.runLater(() -> this.reservations.setAll(reservations));
    }
}
