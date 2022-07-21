package com.pastaebasta.eist.server.controller;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PriceLevel;
import com.pastaebasta.eist.server.service.NearbySearchService;
import com.pastaebasta.eist.server.service.RestaurantService;
import com.pastaebasta.eist.shared.model.Genre;
import com.pastaebasta.eist.shared.model.GoogleRestaurant;
import com.pastaebasta.eist.shared.model.PriceCategory;
import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.testing.Database.DatabaseSeeder;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Restaurant Controller
 * Exposes API to the client, which is used to communicate with the server.
 * <p>
 * This is used internally by Spring Boot to map requests automatically.
 *
 * @author Hisashi & Michael
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;


    //       return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();

    /**
     * Queries Google Maps API for nearby restaurants.
     * TODO: implement method
     *
     * @return List of nearby restaurants.
     */
    @GetMapping("/search")
    public ResponseEntity<List<GoogleRestaurant>> searchRestaurants(@RequestParam(required = true) double lat,
                                                                    @RequestParam(required = true) double lng,
                                                                    @RequestParam(defaultValue = "") String name,
                                                                    @RequestParam(defaultValue = "") String genre,
                                                                    @RequestParam(defaultValue = "cheap") String min_price_category,
                                                                    @RequestParam(defaultValue = "very expensive") String max_price_category,
                                                                    @RequestParam(defaultValue = "5000") int distance) throws IOException, InterruptedException, ApiException {
        if (!genre.isBlank()) {
            name = name + " " + genre;
        }
        PriceLevel minPriceLevel;
        PriceLevel maxPriceLevel;
        switch (min_price_category) {
            case "cheap" -> minPriceLevel = PriceLevel.INEXPENSIVE;
            case "normal" -> minPriceLevel = PriceLevel.MODERATE;
            case "expensive" -> minPriceLevel = PriceLevel.EXPENSIVE;
            case "very expensive" -> minPriceLevel = PriceLevel.VERY_EXPENSIVE;
            default -> throw new IllegalStateException("Unexpected value: " + min_price_category);
        }
        switch (max_price_category) {
            case "cheap" -> maxPriceLevel = PriceLevel.INEXPENSIVE;
            case "normal" -> maxPriceLevel = PriceLevel.MODERATE;
            case "expensive" -> maxPriceLevel = PriceLevel.EXPENSIVE;
            case "very expensive" -> maxPriceLevel = PriceLevel.VERY_EXPENSIVE;
            default -> throw new IllegalStateException("Unexpected value: " + max_price_category);
        }
        var result = new NearbySearchService(new LatLng(lat, lng), name, distance, minPriceLevel, maxPriceLevel);
        return ResponseEntity.ok(result.getCleanedRestaurants());
    }

    /**
     * Queries Google Maps API for nearby restaurants.
     * TODO: implement method properly
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

    /**
     * Returns a restaurant with the given displayId
     *
     * @param restaurantId the displayId of the restaurant
     * @return the restaurant, if it exists
     */
    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(Optional.of(restaurant));
    }

//    @GetMapping("/map")
//    public ResponseEntity<BufferedImage> getMap(String query) {
//        BufferedImage image;
//        try{
//            image = new DynamicMapsService(query).getImage();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//        return ResponseEntity.ok(image);
//    }
}
