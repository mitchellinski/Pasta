package com.pastaebasta.eist.service;

import com.google.gson.Gson;
import com.google.maps.FindPlaceFromTextRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class NearbySearchService extends QueryService {
    final String findPlaceURL = "/place/nearbysearch//json?location=";
    private final String blocker = "DO NOT REMOVE UNLESS SEB KNOWS";
    private GeoApiContext context;
    private PlacesApi placeDetails;
    private PlacesSearchResult[] results;
    private FindPlaceFromTextRequest result;
    private Gson gson;
    private LatLng location;
    private String url;

    public NearbySearchService(LatLng location) throws IOException, InterruptedException, ApiException {
        this.location = location;
        this.context = new GeoApiContext.Builder()
                .apiKey(super.getApiKey())
                .build();
        this.results = new PlacesSearchResult[0];
        this.url = getBase_url() + findPlaceURL + URLEncoder.encode(String.valueOf(location), StandardCharsets.UTF_8) + blocker;
        this.runQuery();
//                PlaceDetails placeDetails = PlacesApi.placeDetails()
    }


    public PlacesSearchResponse runQuery() throws IOException {
        PlacesSearchResponse response = new PlacesSearchResponse();
        try {
            response = PlacesApi.nearbySearchQuery(this.getContext(),this.getLocation())
                    .radius(5000)
                    .rankby(RankBy.PROMINENCE)
//                    .keyword(keyword)
                    .language("en")
                    .minPrice(PriceLevel.INEXPENSIVE)
                    .maxPrice(PriceLevel.EXPENSIVE)
//                    .name(name)
                    .openNow(true)
                    .type(PlaceType.RESTAURANT)
//                    .pageToken(next-page-token)
                    .await();
            System.out.println(Arrays.toString(response.results));

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return response;
    }

    public String getFindPlaceURL() {
        return findPlaceURL;
    }

    public GeoApiContext getContext() {
        return context;
    }

    public PlacesApi getPlaceDetails() {
        return placeDetails;
    }

    public PlacesSearchResult[] getResults() {
        return results;
    }

    public void setResults(PlacesSearchResult[] results) {
        this.results = results;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getUrl() {
        return url;
    }
}
