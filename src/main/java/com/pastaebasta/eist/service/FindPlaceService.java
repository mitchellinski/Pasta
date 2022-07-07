package com.pastaebasta.eist.service;

import com.google.gson.Gson;
import com.google.maps.FindPlaceFromTextRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResult;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FindPlaceService extends QueryService {
    private final String findPlaceURL = "/place/findplacefromtext/json?input=";
    private final String blocker = "DO NOT REMOVE UNLESS SEB KNOWS";
    private GeoApiContext context;
    private PlacesApi placeDetails;
    private PlacesSearchResult[] results;
    private FindPlaceFromTextRequest result;
    private Gson gson;
    private String query;
    private String url;

    public FindPlaceService(String query) throws IOException, InterruptedException, ApiException {
        this.query = query;
        this.context = new GeoApiContext.Builder()
                .apiKey(super.getApiKey())
                .build();
        this.results = new PlacesSearchResult[0];
        this.url = getBase_url() + findPlaceURL + URLEncoder.encode(query, StandardCharsets.UTF_8) + blocker;
        this.runQuery();
//                PlaceDetails placeDetails = PlacesApi.placeDetails()
    }

    public PlacesSearchResult runQuery() throws IOException {
        PlacesSearchResult candidate = new PlacesSearchResult();
        try {
            FindPlaceFromText response = PlacesApi.findPlaceFromText(this.getContext(), this.getQuery(), FindPlaceFromTextRequest.InputType.TEXT_QUERY)
                    .fields(
                            FindPlaceFromTextRequest.FieldMask.PLACE_ID
                            , FindPlaceFromTextRequest.FieldMask.FORMATTED_ADDRESS
                            , FindPlaceFromTextRequest.FieldMask.NAME
                            , FindPlaceFromTextRequest.FieldMask.ICON
                            , FindPlaceFromTextRequest.FieldMask.OPENING_HOURS
                            , FindPlaceFromTextRequest.FieldMask.PRICE_LEVEL
                            , FindPlaceFromTextRequest.FieldMask.RATING
                            , FindPlaceFromTextRequest.FieldMask.TYPES
                            , FindPlaceFromTextRequest.FieldMask.GEOMETRY
                            , FindPlaceFromTextRequest.FieldMask.PHOTOS
                    )
                    .locationBias(new FindPlaceFromTextRequest.LocationBiasIP())
                    .await();
            candidate = response.candidates[0];
            System.out.println(candidate.name);
            System.out.println(candidate.formattedAddress);
            Photo photo = candidate.photos[0];
            System.out.println(candidate.rating);
            System.out.println(candidate.geometry);
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return candidate;
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

    public String getQuery() {
        return query;
    }

    public String getUrl() {
        return url;
    }
}
