package com.pastaebasta.eist.server.controller;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PriceLevel;
import com.pastaebasta.eist.server.service.NearbySearchService;
import com.pastaebasta.eist.shared.model.Genre;
import com.pastaebasta.eist.shared.model.PriceCategory;
import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.testing.Database.DatabaseSeeder;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Restaurant Controller
 * Exposes API to the client, which is used to communicate with the server.
 *
 * This is used internally by Spring Boot to map requests automatically.
 *
 * @author Hisashi
 * @author Michael
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private PriceLevel stringToPriceLevel(String priceCategory) {
        return switch (priceCategory) {
            case "cheap" -> PriceLevel.INEXPENSIVE;
            case "normal" -> PriceLevel.MODERATE;
            case "expensive" -> PriceLevel.EXPENSIVE;
            case "very expensive" -> PriceLevel.VERY_EXPENSIVE;
            default -> throw new IllegalStateException("Unexpected value: " + priceCategory);
        };
    }

    /**
     * Queries Google Maps API for nearby restaurants.
     *
     * @return List of nearby Restaurants.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(
            @RequestParam(required = true) double lat,
            @RequestParam(required = true) double lng,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String genre,
            @RequestParam(defaultValue = "cheap") String min_price_category,
            @RequestParam(defaultValue = "very expensive") String max_price_category,
            @RequestParam(defaultValue = "5000") int distance) throws IOException, InterruptedException, ApiException {

        if (!genre.isBlank()) {
            name = name + " " + genre;
        }

        PriceLevel minPriceLevel = stringToPriceLevel(min_price_category);
        PriceLevel maxPriceLevel = stringToPriceLevel(max_price_category);

        var result = new NearbySearchService(new LatLng(lat, lng), name, distance, minPriceLevel, maxPriceLevel);
        return ResponseEntity.ok(result.runSearch());
    }

    /**
     * Test - returns 50 randomly generated restaurants.
     * IDs and names are not random for testing.
     *
     * @return List of nearby restaurants.
     */
    @GetMapping("/test_search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) PriceCategory price_category,
            @RequestParam(required = false) long distance) {

        return ResponseEntity.ok(DatabaseSeeder.generateSimpleRestaurants(50));
    }
}
