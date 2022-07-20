package com.pastaebasta.eist.server.service;

import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.StaticMapsApi;
import com.google.maps.StaticMapsRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Size;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class StaticMapsService extends QueryService {
    private final int WIDTH = 640;
    private final int HEIGHT = 480;

    private final String geoURL = "/geocode/json?address=";
    //BELOW TO BLOCK API CALLS, these are a fraction of a cent per request
    private final String blocker = "DO NOT REMOVE UNLESS SEB KNOWS";
    private final GeoApiContext context;
    private GeocodingResult[] results;
    private Gson gson;
    private String query;
    private BufferedImage image;
//    private final String url;

    public StaticMapsService(String query) throws IOException, InterruptedException, ApiException {
        this.context = new GeoApiContext.Builder()
                .apiKey(super.getApiKey())
                .build();
        this.results = null;
        runQuery(query);
//        this.url = ;
    }

    public void runQuery(String query) throws IOException, InterruptedException, ApiException {
        StaticMapsRequest request = StaticMapsApi.newRequest(this.getContext(), new Size(WIDTH, HEIGHT));
        request.center(query);
        request.zoom(16);
        ByteArrayInputStream imgData = new ByteArrayInputStream(request.await().imageData);
//        BufferedImage test = ImageIO.read(imgData);
        setImage(ImageIO.read(imgData));
    }

    public String getGeoURL() {
        return geoURL;
    }

    public String getBlocker() {
        return blocker;
    }

    public GeoApiContext getContext() {
        return context;
    }

    public GeocodingResult[] getResults() {
        return results;
    }

    public Gson getGson() {
        return gson;
    }

    public String getQuery() {
        return query;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
