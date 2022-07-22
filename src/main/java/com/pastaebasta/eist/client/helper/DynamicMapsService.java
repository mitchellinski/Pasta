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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        googleMapView.setVisible(true);
        googleMapView.setKey(this.apiKey);
        googleMapView.addMapInitializedListener(() -> configureMap());
    }

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

//        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
//            LatLong latLong = event.getLatLong();
//            latitudeLabel.setText(formatter.format(latLong.getLatitude()));
//            longitudeLabel.setText(formatter.format(latLong.getLongitude()));
//        });
//
//        //Add markers to the map
//        MarkerOptions markerOptions1 = new MarkerOptions();
//        markerOptions1.position(joeSmithLocation);
//
//        MarkerOptions markerOptions2 = new MarkerOptions();
//        markerOptions2.position(joshAndersonLocation);
//
//        MarkerOptions markerOptions3 = new MarkerOptions();
//        markerOptions3.position(bobUnderwoodLocation);
//
//        MarkerOptions markerOptions4 = new MarkerOptions();
//        markerOptions4.position(tomChoiceLocation);
//
//        MarkerOptions markerOptions5 = new MarkerOptions();
//        markerOptions5.position(fredWilkieLocation);
//
//        Marker joeSmithMarker = new Marker(markerOptions1);
//        Marker joshAndersonMarker = new Marker(markerOptions2);
//        Marker bobUnderwoodMarker = new Marker(markerOptions3);
//        Marker tomChoiceMarker = new Marker(markerOptions4);
//        Marker fredWilkieMarker = new Marker(markerOptions5);
//
//        map.addMarker(joeSmithMarker);
//        map.addMarker(joshAndersonMarker);
//        map.addMarker(bobUnderwoodMarker);
//        map.addMarker(tomChoiceMarker);
//        map.addMarker(fredWilkieMarker);
//
//        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
//        infoWindowOptions.content("<h2>Fred Wilkie</h2>"
//                + "Current Location: Safeway<br>"
//                + "ETA: 45 minutes");
//
//        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
//        fredWilkeInfoWindow.open(map, fredWilkieMarker);

    }


}

