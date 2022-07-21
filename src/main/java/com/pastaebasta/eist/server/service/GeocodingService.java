package com.pastaebasta.eist.server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GeocodingService extends QueryService {
    private final String geoURL = "/geocode/json?address=";
    //BELOW TO BLOCK API CALLS, these are a fraction of a cent per request
    private final String blocker = "DO NOT REMOVE UNLESS SEB KNOWS";
    private final GeoApiContext context;
    private GeocodingResult[] results;
    private Gson gson;
    private String query;
    private final String url;

    public GeocodingService(String query) throws IOException, InterruptedException, ApiException {
        this.context = new GeoApiContext.Builder()
                .apiKey(super.getApiKey())
                .build();
        this.results = new GeocodingResult[0];
        this.url = getBase_url() + geoURL + URLEncoder.encode(query, StandardCharsets.UTF_8);
        this.runQuery();
    }

    public Gson runQuery() {
        try {
            this.setResults(GeocodingApi.geocode(this.getContext(), this.getUrl()).await());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        this.setGson(new GsonBuilder().setPrettyPrinting().create());
//        System.out.println(this.gson.toJson(results[0].addressComponents));
//        System.out.println(this.gson.toJson(results[0].geometry));
        context.shutdown();
        return this.getGson();
    }

    public String getGeoURL() {
        return geoURL;
    }

    public GeoApiContext getContext() {
        return context;
    }

    public GeocodingResult[] getResults() {
        return results;
    }

    public void setResults(GeocodingResult[] results) {
        this.results = results;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public String getQuery() {
        return query;
    }

    public String getUrl() {
        return url;
    }
}
