package com.pastaebasta.eist.server.service;

import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.pastaebasta.eist.shared.model.GoogleRestaurant;
import com.pastaebasta.eist.shared.model.Restaurant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NearbySearchService extends QueryService {
    final String findPlaceURL = "/place/nearbysearch//json?location=";
    private final String blocker = "DO NOT REMOVE UNLESS SEB KNOWS";
    private final GeoApiContext context;
    private final LatLng location;
    private final String url;
    private PlacesApi placeDetails;
    private PlacesSearchResult[] results;
    private List<Restaurant> cleanedRestaurants;
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
                    //.openNow(true)
                    .type(PlaceType.RESTAURANT)
                    .await();

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        setResults(response.results);
        setCleanedRestaurants(cleanResults());
        return response;
    }

    private Pair<LocalTime, LocalTime> extractOpeningHours(OpeningHours openingHours) {
        LocalTime open = LocalTime.of(8,0);
        LocalTime close = LocalTime.of(22,0);

        if (openingHours != null && openingHours.periods != null) {
            for (OpeningHours.Period period : openingHours.periods) {
                open = period.open.time;
                close = period.close.time;
                break;
            }
        }

        return Pair.of(open, close);
    }

    private List<Restaurant> cleanResults() {
        PlacesSearchResult[] input = this.getResults();
        List<Restaurant> restaurantList = new ArrayList<>();

        for (PlacesSearchResult result : input) {
            Pair<LocalTime, LocalTime> pair = extractOpeningHours(result.openingHours);
            Restaurant tempRestaurant = Restaurant.builder()
                .name(result.name)
                .address(result.formattedAddress)
                .coordinates(result.geometry.location)
                .ratingAverage(Math.round(result.rating * 10.0) / 10.0)
                .openingTime(pair.getFirst().getHour())
                .closingTime(pair.getSecond().getHour())
                .pictures(result.photos)
                .build();
            restaurantList.add(tempRestaurant);
        }
        return restaurantList;
    }

}
