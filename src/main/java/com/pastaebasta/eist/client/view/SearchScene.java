package com.pastaebasta.eist.client.view;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.client.helper.GeocodingService;
import com.pastaebasta.eist.shared.model.Genre;
import com.pastaebasta.eist.shared.model.PriceCategory;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SearchScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;
    private final ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();
    private TableView<Restaurant> table;
    private double width;
    private LatLng latLng;

    public SearchScene(ClientController clientController, ViewApplication application, LatLng latLng) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        width = application.getStage().getScene().getWidth() / 7.0;
        this.clientController = clientController;
        this.application = application;
        this.latLng = latLng;
        if (this.latLng == null) {
            createpop();
        }
        init();
    }

    private void init() {
        Label headline = new Label("Search for Restaurant");
        headline.setFont(new Font("Arial", 20));

        Button switchToMapButton = new Button("Switch to Map View");
        switchToMapButton.setOnAction(event -> {
            try {
                application.showMapsScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.table = new TableView<>(restaurants);

        var nameColumn = new TableColumn<Restaurant, String>("Name");
        nameColumn.setSortable(true);
        nameColumn.setPrefWidth(width);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        var addressColumn = new TableColumn<Restaurant, String>("Address");
        addressColumn.setSortable(true);
        addressColumn.setPrefWidth(width);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        var genreColumn = new TableColumn<Restaurant, String>("Type");
        genreColumn.setSortable(true);
        genreColumn.setPrefWidth(width);
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantGenre"));
        var priceColumn = new TableColumn<Restaurant, String>("Price Category");
        priceColumn.setSortable(true);
        priceColumn.setPrefWidth(width);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceCategory"));
        var ratingColumn = new TableColumn<Restaurant, String>("Rating");
        ratingColumn.setSortable(true);
        ratingColumn.setPrefWidth(width);
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("ratingAverage"));
        var distanceColumn = new TableColumn<Restaurant, Double>("Distance (in meters)");
        distanceColumn.setSortable(true);
        distanceColumn.setPrefWidth(width);
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        table.getColumns().addAll(nameColumn, addressColumn, genreColumn, priceColumn, ratingColumn, distanceColumn);
        addButtonToTable(width);

        var vBox = new VBox(10, headline, switchToMapButton, createSearchBar(), table, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private void addButtonToTable(double width) {
        TableColumn<Restaurant, Void> colBtn = new TableColumn("Reservation");
        colBtn.setPrefWidth(width);

        Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Restaurant, Void> call(final TableColumn<Restaurant, Void> param) {
                final TableCell<Restaurant, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Reserve");

                    {
                        btn.setPrefWidth(width);
                        btn.setOnAction((ActionEvent event) -> {
                            Restaurant restaurant = getTableView().getItems().get(getIndex());
                            application.setRestaurant(restaurant);
                            application.showRestaurantScene(restaurant);
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
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);
    }


    private GridPane createSearchBar() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(5);
        grid.setHgap(15);

        Label searchLabel = new Label("Search for a Restaurant");
        searchLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(searchLabel, 0, 0);

        TextField searchField = new TextField();
        searchField.setPromptText("Restaurant name");
        GridPane.setConstraints(searchField, 0, 1);

        Label genreLabel = new Label("Type");
        genreLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(genreLabel, 1, 0);

        ChoiceBox<String> genreBox = new ChoiceBox<>();
        List<String> genres =
                Arrays.stream(Genre.values())
                        .map(genre -> genre.toString().replace('_', ' '))
                        .map(genre -> genre.charAt(0) + genre.substring(1).toLowerCase())
                        .toList();
        genreBox.getItems().addAll(genres);
        GridPane.setConstraints(genreBox, 1, 1);


        Label minPriceLabel = new Label("Min Price Category");
        minPriceLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(minPriceLabel, 2, 0);

        ChoiceBox<String> minPriceBox = new ChoiceBox<>();
        List<String> categories =
                Arrays.stream(PriceCategory.values())
                        .map(p -> p.toString().replace('_', ' '))
                        .map(String::toLowerCase)
                        .toList();
        minPriceBox.getItems().addAll(categories);
        minPriceBox.setValue("cheap");
        GridPane.setConstraints(minPriceBox, 2, 1);

        Label maxPriceLabel = new Label("Max Price Category");
        maxPriceLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(maxPriceLabel, 3, 0);

        ChoiceBox<String> maxPriceBox = new ChoiceBox<>();
        maxPriceBox.getItems().addAll(categories);
        maxPriceBox.setValue("very expensive");
        GridPane.setConstraints(maxPriceBox, 3, 1);

        Label distanceLabel = new Label("Set the distance (in meter)");
        distanceLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(distanceLabel, 4, 0);
        GridPane.setColumnSpan(distanceLabel, 2);
        TextField distanceField = new TextField("2000");
        distanceField.setPrefWidth(50);
        GridPane.setConstraints(distanceField, 5, 1);
        Slider slider = new Slider(0, 50000, 20000);
        slider.setPrefWidth(200);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(5000);
        slider.setMinorTickCount(3);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                distanceField.setText(String.valueOf(newValue.intValue()));
            }
        });
        distanceField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                slider.setValue(Integer.parseInt(distanceField.getText().trim()));
            }
        });
        GridPane.setConstraints(slider, 4, 1);
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> {
//                clientController.search(searchField.getText(), Genre.GERMAN, minPriceBox.getValue(), 5000, this::setRestaurants)
            clientController.search(this.latLng, searchField.getText(), genreBox.getValue()
                    , minPriceBox.getValue(), maxPriceBox.getValue(), Integer.parseInt(distanceField.getText().trim()), this::setRestaurants);
        });
//        searchField.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                clientController.search(searchField.getText(), Genre.GERMAN, minPriceBox.getValue(), maxPriceBox.getValue(),
//                        5000, this::setRestaurants);
//            }
//        });
        GridPane.setConstraints(searchButton, 6, 1);

        grid.getChildren().addAll(searchLabel, searchField, genreLabel, genreBox, minPriceLabel, minPriceBox,
                maxPriceLabel, maxPriceBox, distanceLabel, slider, distanceField, searchButton);
        grid.setAlignment(Pos.TOP_CENTER);

        return grid;
    }

    private void setRestaurants(List<Restaurant> restaurants) {
        Platform.runLater(() -> this.restaurants.setAll(restaurants));
    }

    private void createpop() {
        Popup popup = new Popup();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(5);
        grid.setHgap(5);

        Label headline = new Label("Please enter an address/zip-code to start searching from: ");
        headline.setFont(new Font("Arial", 20));
        GridPane.setConstraints(headline, 1, 0);

        TextField address = new TextField();
        address.setPromptText("Address");
        address.setPrefWidth(150);
        GridPane.setConstraints(address, 1, 1);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            popup.hide();
            application.showHomeScene();
        });
        GridPane.setConstraints(backButton, 0, 1);

        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> {
            try {
                if (address.getText().isBlank()) {
                    System.out.println(address.getText());
                    return;
                }

                if ("$test".equalsIgnoreCase(address.getText())) {
                    this.latLng = new LatLng(48.148849, 11.568000);
                } else {
                    var temp = new GeocodingService(address.getText()).getResults();
                    application.setLatLng(new LatLng(temp[0].geometry.location.lat, temp[0].geometry.location.lng));
                    this.latLng = application.getLatLng();
                }
            } catch (IOException | InterruptedException | ApiException e) {
                e.printStackTrace();
            }
            popup.hide();
            init();
        });
        GridPane.setConstraints(searchButton, 2, 1);

        grid.getChildren().addAll(headline, address, backButton, searchButton);
        grid.setAlignment(Pos.CENTER);

        var vBox = new VBox(10, grid);
        vBox.setAlignment(Pos.CENTER);

        vBox.setPrefWidth(800);
        vBox.setPrefHeight(200);
        vBox.isResizable();
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
        popup.getContent().add(vBox);
        popup.show(application.getStage());
        popup.centerOnScreen();
    }
}
