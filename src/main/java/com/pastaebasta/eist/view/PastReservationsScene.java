package com.pastaebasta.eist.view;

import com.pastaebasta.eist.controller.ClientController;
import com.pastaebasta.eist.controller.ServerController;
import com.pastaebasta.eist.model.Reservation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;


public class PastReservationsScene extends Scene {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 500;

    private final ClientController clientController;
    private final ViewApplication application;
    private final ObservableList<Reservation> reservations;
    private final TableView<Reservation> table;

    public PastReservationsScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), WIDTH, HEIGHT);
        this.clientController = clientController;
        this.application = application;
        this.reservations = FXCollections.observableArrayList();
        clientController.getCustomerReservations(application.getCustomer().getCustomerId(), this::setReservations);
        table = new TableView<>(reservations);

        Label headline = new Label("Past Reservations");
        headline.setFont(new Font("Arial", 20));

        var idColumn = new TableColumn<Reservation, String>("ID");
        idColumn.setSortable(false);
        idColumn.setPrefWidth(WIDTH / 5);
        var dateColumn = new TableColumn<Reservation, String>("Date");
        dateColumn.setSortable(true);
        dateColumn.setPrefWidth(WIDTH / 5);
        var nameColumn = new TableColumn<Reservation, String>("Restaurant");
        nameColumn.setSortable(true);
        nameColumn.setPrefWidth(WIDTH / 5);
        var visitorColumn = new TableColumn<Reservation, String>("Visitors");
        visitorColumn.setSortable(true);
        visitorColumn.setPrefWidth(WIDTH / 5);
        var confirmedColumn = new TableColumn<Reservation, String>("Confirmed");
        confirmedColumn.setSortable(true);
        confirmedColumn.setPrefWidth(WIDTH / 5);

        table.getColumns().addAll(idColumn, dateColumn, nameColumn, visitorColumn, confirmedColumn);

        var vBox = new VBox(10, headline, table, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private void setReservations(List<Reservation> reservations) {
        Platform.runLater(() -> this.reservations.setAll(reservations));
    }
}
