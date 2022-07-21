package com.pastaebasta.eist.client.view;

import com.google.maps.model.LatLng;
import com.pastaebasta.eist.client.controller.ClientController;
import com.pastaebasta.eist.server.service.DynamicMapsService;
import com.pastaebasta.eist.shared.model.Genre;
import com.pastaebasta.eist.shared.model.PriceCategory;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MapsScene extends Scene {

    private final ClientController clientController;
    private final ViewApplication application;
    private final LatLng latLng;

    public MapsScene(ClientController clientController, ViewApplication application, LatLng latLng) throws IOException {
        super(new VBox(), application.getStage().getScene().getWidth(), application.getStage().getScene().getHeight());
        this.clientController = clientController;
        this.application = application;
        this.latLng = latLng;

        Label headline = new Label("Search for a Restaurant");
        headline.setFont(new Font("Arial", 20));

        Button switchToListButton = new Button("Switch to List View");
        switchToListButton.setOnAction(event -> application.showSearchScene(latLng));

        URL url = new File("src/main/resources/Scene.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        DynamicMapsService service = new DynamicMapsService(latLng);
        loader.setController(service);
        Parent map = loader.load();
        var vBox = new VBox(10, headline, switchToListButton, map, application.createBackButton());
        vBox.setAlignment(Pos.CENTER);

        setRoot(vBox);
//        setRoot(FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml")))
    }
}
