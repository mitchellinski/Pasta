package com.pastaebasta.eist.server.service;

import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.testing.Database.DatabaseSeeder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * NearbySearchService
 * Acts as an Adapter for the Google Maps API.
 *
 * This class has to be initialized with the specific query parameters
 * To run the query, call runSearch(), which will return a list of restaurants.
 */
@Getter
@Setter
public class NearbySearchService extends QueryService {
    private static final String findPlaceURL = "/place/nearbysearch//json?location=";
    private static final Random RANDOM = new Random();
    private static final PlaceType[] types = {
            PlaceType.RESTAURANT, PlaceType.BAR, PlaceType.CAFE,
            PlaceType.BAKERY, PlaceType.MEAL_TAKEAWAY
    };

    private final GeoApiContext context;
    private final LatLng location;
    private final String url;
    private PlacesApi placeDetails;
    private List<PlacesSearchResult> results;
    private List<Restaurant> cleanedRestaurants;
    private Gson gson;

    private String query;
    private int radius;
    private PriceLevel minPrice;
    private PriceLevel maxPrice;
    private PlaceType placeType;

    public NearbySearchService(LatLng location, String query, int radius, PriceLevel minPrice, PriceLevel maxPrice) throws IOException, InterruptedException, ApiException {
        this.location = location;
        this.context = new GeoApiContext.Builder()
                .apiKey(super.getApiKey())
                .build();
        this.results = new ArrayList<>();
        this.url = getBase_url() + findPlaceURL + URLEncoder.encode(String.valueOf(location), StandardCharsets.UTF_8);
        this.query = query;
        this.radius = radius;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * Executes a search, using the Google API, with the provided query parameters.
     * Will go through all types, unless there are already 50 restaurants.
     * @return List of Restaurants
     * @throws IOException
     */
    public List<Restaurant> runSearch() throws IOException {
        for (PlaceType type : types) {
            if (results.size() > 50) {
                break;
            }
            List<PlacesSearchResult> newResults = this.runQuery(this.query, this.radius, this.minPrice, this.maxPrice, type);
            this.results.addAll(newResults);
        }

        List<Restaurant> restaurants = transformResultsIntoRestaurantList(this.results);
        this.results.clear();

        return restaurants;
    }

    /**
     * Formats and ships request to google.
     * Returns a list of PlaceSearchResult
     * @param query query string
     * @param radius maximum radius
     * @param minPrice min price
     * @param maxPrice maximum price
     * @param placeType place type
     * @return List of PlaceSearchResult
     * @throws IOException
     */
    public List<PlacesSearchResult> runQuery(String query, int radius, PriceLevel minPrice, PriceLevel maxPrice, PlaceType placeType) throws IOException {
        PlacesSearchResponse response = new PlacesSearchResponse();
        try {
            response = PlacesApi.nearbySearchQuery(this.getContext(), this.getLocation())
                .radius(radius)
                .rankby(RankBy.PROMINENCE)
                .language("en")
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .type(placeType)
                .await();
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

        return placesSearchResultsToList(response.results);
    }

    /**
     * Transforms array of PlaceSearchResult into a list for better data manipulation.
     * @param array array to be transformed
     * @return List of PlaceSearchResult
     */
    private List<PlacesSearchResult> placesSearchResultsToList(PlacesSearchResult[] array) {
        List<PlacesSearchResult> result = new ArrayList<>();
        for(PlacesSearchResult r : array) {
            result.add(r);
        }
        return result;
    }

    /**
     * Calculates the distance between two given LatLng values
     * @param a first coordinate tuple
     * @param b second coordinate tuple
     * @return distance between coordinates
     */
    private double distanceBetweenCoordinates(LatLng a, LatLng b) {
        double pi = Math.PI;
        double r2d = 180.0D / pi;
        double d2r = pi / 180.0D;
        double d2km = 111189.57696D * r2d;
        double x = a.lat * d2r;
        double y = b.lat * d2r;
        return Math.acos(Math.sin(x) * Math.sin(y) + Math.cos(x) * Math.cos(y) * Math.cos(d2r * (a.lng - b.lng))) * d2km;
    }

    /**
     * Transform PlaceSearchResult list into a restaurant list.
     * Creates necessary coordinates, formats rating, gets a price category, etc
     * @param input PlaceSearchResult list
     * @return Restaurant List
     */
    private List<Restaurant> transformResultsIntoRestaurantList(List<PlacesSearchResult> input) {
        List<Restaurant> restaurantList = new ArrayList<>();
        for (PlacesSearchResult result : input) {
            Pair<LocalTime, LocalTime> pair = extractOpeningHours(result.openingHours);
            String genre = result.types[RANDOM.nextInt(0, result.types.length)].replaceAll("_", " ");
            genre = genre.substring(0,1).toUpperCase() + genre.substring(1).toLowerCase();
            System.out.println(result);
            Restaurant tempRestaurant = Restaurant.builder()
                .restaurantId(result.placeId)
                .name(result.name)
                .address(result.vicinity)
                .coordinates(result.geometry.location)
                .priceCategory(DatabaseSeeder.getSeededRandomPriceCategory(Restaurant.IdToInteger(result.placeId)))
                .ratingAverage(Math.round(result.rating * 10.0) / 10.0)
                .openingHours(result.openingHours)
                .distance(Math.round(distanceBetweenCoordinates(location, result.geometry.location) * 10.0) / 10.0)
                .openingTime(pair.getFirst().getHour())
                .closingTime(pair.getSecond().getHour())
                .restaurantGenre(genre)
                .pictures(result.photos)
                .build();
            restaurantList.add(tempRestaurant);
        }
        return restaurantList;
    }

    /**
     * Helper to get a place's opening hours.
     * Defaults to 8am-10pm
     * @param openingHours
     * @return
     */
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
}
