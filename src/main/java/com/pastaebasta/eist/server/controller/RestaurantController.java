package com.pastaebasta.eist.server.controller;

import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.server.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Restaurant Controller
 * Exposes API to the client, which is used to communicate with the server.
 *
 * This is used internally by Spring Boot to map requests automatically.
 *
 * @version 1.0
 * @author Hisashi & Michael
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    /**
     * Queries Google Maps API for nearby restaurants.
     * TODO: implement method
     * @return List of nearby restaurants.
     */
    @GetMapping("/search")
    public ResponseEntity<String> searchRestaurants() {
        return ResponseEntity.ok("Yeah!");
    }

    /**
     * Returns a restaurant with the given displayId
     * @param restaurantId the displayId of the restaurant
     * @return the restaurant, if it exists
     */
    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if(restaurant == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(Optional.of(restaurant));
    }
}
