package com.pastaebasta.eist.view;

import com.pastaebasta.eist.controller.ClientController;
import com.pastaebasta.eist.controller.ServerController;
import com.pastaebasta.eist.model.Genre;
import com.pastaebasta.eist.model.PriceCategory;
import com.pastaebasta.eist.model.Restaurant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SearchScene extends Scene {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 500;

    private final ClientController clientController;
    private final ViewApplication application;
    private TableView<Restaurant> table;


    public SearchScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), WIDTH, HEIGHT);
        this.clientController = clientController;
        this.application = application;

        Label headline = new Label("Search for Restaurant");
        headline.setFont(new Font("Arial", 20));

        this.table = new TableView<>();

        var nameColumn = new TableColumn<Restaurant, String>("Name");
        nameColumn.setSortable(true);
        nameColumn.setPrefWidth(WIDTH / 5);
        var addressColumn = new TableColumn<Restaurant, String>("Address");
        addressColumn.setSortable(true);
        addressColumn.setPrefWidth(WIDTH / 5);
        var genreColumn = new TableColumn<Restaurant, String>("Genre");
        genreColumn.setSortable(true);
        genreColumn.setPrefWidth(WIDTH / 5);
        var priceColumn = new TableColumn<Restaurant, String>("Price Category");
        priceColumn.setSortable(true);
        priceColumn.setPrefWidth(WIDTH / 5);
        var ratingColumn = new TableColumn<Restaurant, String>("Rating");
        ratingColumn.setSortable(true);
        ratingColumn.setPrefWidth(WIDTH / 5);

        table.getColumns().addAll(nameColumn, addressColumn, genreColumn, priceColumn, ratingColumn);

        var vBox = new VBox(10, headline, createSearchBar(), table, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private GridPane createSearchBar() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label searchLabel = new Label("Search for a Restaurant");
        searchLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(searchLabel, 0, 0);

        TextField searchField = new TextField();
        searchField.setPromptText("Restaurant name");
        GridPane.setConstraints(searchField, 0, 1);

        Label genreLabel = new Label("Type");
        genreLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(genreLabel, 1, 0);

        ChoiceBox<Genre> genreBox = new ChoiceBox<>();
        genreBox.getItems().addAll(Genre.values());
        GridPane.setConstraints(genreBox, 1, 1);

        Label priceLabel = new Label("Pice Category");
        priceLabel.setFont(new Font("Arial", 10));
        GridPane.setConstraints(priceLabel, 2, 0);

        ChoiceBox<PriceCategory> priceBox = new ChoiceBox<>();
        priceBox.getItems().addAll(PriceCategory.values());
        GridPane.setConstraints(priceBox, 2, 1);

        Button searchButton = new Button("Search");
//        searchButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//            }
//        });
        GridPane.setConstraints(searchButton, 3, 1);

        grid.getChildren().addAll(searchLabel, searchField, genreLabel, genreBox, priceLabel, priceBox, searchButton);
        grid.setAlignment(Pos.TOP_CENTER);

        return grid;
    }
}
