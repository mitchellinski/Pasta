package com.pastaebasta.eist.client.view;

import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.shared.model.Genre;
import com.pastaebasta.eist.shared.model.PriceCategory;
import com.pastaebasta.eist.shared.model.Restaurant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;

public class SearchScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;
    private final TableView<Restaurant> table;


    public SearchScene(ClientController clientController, ViewApplication application) {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        int width = (int) application.getStage().getScene().getWidth();
        this.clientController = clientController;
        this.application = application;

        Label headline = new Label("Search for Restaurant");
        headline.setFont(new Font("Arial", 20));

        this.table = new TableView<>();

        var nameColumn = new TableColumn<Restaurant, String>("Name");
        nameColumn.setSortable(true);
        nameColumn.setPrefWidth(width / 5);
        var addressColumn = new TableColumn<Restaurant, String>("Address");
        addressColumn.setSortable(true);
        addressColumn.setPrefWidth(width / 5);
        var genreColumn = new TableColumn<Restaurant, String>("Genre");
        genreColumn.setSortable(true);
        genreColumn.setPrefWidth(width / 5);
        var priceColumn = new TableColumn<Restaurant, String>("Price Category");
        priceColumn.setSortable(true);
        priceColumn.setPrefWidth(width / 5);
        var ratingColumn = new TableColumn<Restaurant, String>("Rating");
        ratingColumn.setSortable(true);
        ratingColumn.setPrefWidth(width / 5);

        table.getColumns().addAll(nameColumn, addressColumn, genreColumn, priceColumn, ratingColumn);

        var vBox = new VBox(10, headline, createSearchBar(), table, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private GridPane createSearchBar() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(5);
        grid.setHgap(5);

        Label searchLabel = new Label("Search for a Restaurant");
        searchLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(searchLabel, 0, 0);

        TextField searchField = new TextField();
        searchField.setPromptText("Restaurant name");
        GridPane.setConstraints(searchField, 0, 1);

        Label genreLabel = new Label("Type");
        genreLabel.setFont(new Font("Arial", 20));
        GridPane.setConstraints(genreLabel, 1, 0);

        ChoiceBox<String> genreBox = new ChoiceBox<>();
        List<String> genres =
                Arrays.stream(Genre.values())
                        .map(genre -> genre.toString().replace('_', ' '))
                        .map(genre -> genre.charAt(0) + genre.substring(1).toLowerCase())
                        .toList();
        genreBox.getItems().addAll(genres);
        GridPane.setConstraints(genreBox, 1, 1);

        Label priceLabel = new Label("Price Category");
        priceLabel.setFont(new Font("Arial", 20));
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
