package com.pastaebasta.eist.server.service;

import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.pastaebasta.eist.shared.model.GoogleRestaurant;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NearbySearchService extends QueryService {
    final String findPlaceURL = "/place/nearbysearch//json?location=";
    private final String blocker = "DO NOT REMOVE UNLESS SEB KNOWS";
    private final GeoApiContext context;
    private final LatLng location;
    private final String url;
    private PlacesApi placeDetails;
    private PlacesSearchResult[] results;
    private List<GoogleRestaurant> cleanedRestaurants;
    private Gson gson;

    public NearbySearchService(LatLng location, String query, int radius, PriceLevel minPrice, PriceLevel maxPrice) throws IOException, InterruptedException, ApiException {
        this.location = location;
        this.context = new GeoApiContext.Builder()
                .apiKey(super.getApiKey())
                .build();
        this.results = new PlacesSearchResult[0];
        this.url = getBase_url() + findPlaceURL + URLEncoder.encode(String.valueOf(location), StandardCharsets.UTF_8);
        this.runQuery(query, radius, minPrice, maxPrice);
    }


    public PlacesSearchResponse runQuery(String query, int radius, PriceLevel minPrice, PriceLevel maxPrice) throws IOException {
        PlacesSearchResponse response = new PlacesSearchResponse();
        try {
            response = PlacesApi.nearbySearchQuery(this.getContext(), this.getLocation())
                   // .keyword(query)
                    .radius(radius)
                    .rankby(RankBy.PROMINENCE)
                    .language("en")
                    .minPrice(minPrice)
                    .maxPrice(maxPrice)
                    .openNow(true)
                    .type(PlaceType.RESTAURANT)
                    .await();

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        setResults(response.results);
        setCleanedRestaurants(cleanResults());
        return response;
    }

    private List<GoogleRestaurant> cleanResults() {
        PlacesSearchResult[] input = this.getResults();
        List<GoogleRestaurant> restaurantList = new ArrayList<GoogleRestaurant>();
        for (PlacesSearchResult curr : input) {
            GoogleRestaurant tempRestaurant = new GoogleRestaurant(curr.placeId
                    , curr.name
                    , curr.formattedAddress
                    , curr.geometry.location
                    , curr.rating
                    , curr.openingHours
                    , curr.photos);
            restaurantList.add(tempRestaurant);
        }
        return restaurantList;

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

    public List<GoogleRestaurant> getCleanedRestaurants() {
        return cleanedRestaurants;
    }

    public void setCleanedRestaurants(List<GoogleRestaurant> cleanedRestaurants) {
        this.cleanedRestaurants = cleanedRestaurants;
    }
}
