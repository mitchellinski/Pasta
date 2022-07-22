package com.pastaebasta.eist.client.helper;


import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import com.google.maps.model.LatLng;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * SERVICE - Dynamic Maps Service
 * <p>
 * Handles the map implementation in conjunction with an .fxml file to:
 * Display a GUI map, along with allowing for searching, filtering, scrolling, and individual place investigations
 *
 * @author Sebastian
 */
public class DynamicMapsService implements Initializable {
    private final String apiKey = "AIzaSyDs-IHgDUlF3ZHzK2ATjLiGZs9_NnYXr6U";

    @FXML
    private Label latitudeLabel;

    @FXML
    private Label longitudeLabel;

    @FXML
    private GoogleMapView googleMapView;

    private GoogleMap map;

    private DecimalFormat formatter = new DecimalFormat("###.00000");

    private LatLng latLng;

    public DynamicMapsService(LatLng latLng) {
        this.latLng = latLng;
    }

    /**
     * Initializes the map, pulls in the API key and resources needed
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        googleMapView.setVisible(true);
        googleMapView.setKey(this.apiKey);
        googleMapView.addMapInitializedListener(() -> configureMap());
    }

    /**
     * Configures map options, calling the dynamic user Lat/Lng input and allowing certain levels of control.
     */
    protected void configureMap() {
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(this.latLng.lat, this.latLng.lng))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(true)
                .panControl(true)
                .rotateControl(true)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(true)
                .clickableIcons(true)
                .streetViewControl(true)
                .fullscreenControl(true)
                .zoom(12);

        map = googleMapView.createMap(mapOptions, false);

    }


}

