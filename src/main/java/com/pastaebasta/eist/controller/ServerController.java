package com.pastaebasta.eist.controller;

import com.pastaebasta.eist.model.*;
import com.pastaebasta.eist.service.PBReservationService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ServerController
 * Exposes API to the client, which is used to communicate with the server.
 *
 * This is used internally by Spring Boot to map requests automatically.
 *
 * @version 1.0
 * @author Hisashi
 */
@RestController
@AllArgsConstructor
public class ServerController {
    private PBReservationService reservationService;

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
     * Create new reservation.
     * Checks DB to see if reservation slot and table are available.
     * If created, return persisted instance.
     * @param reservation Reservation to be added
     * @return Persisted reservation instance.
     */
    @PostMapping("/api/v1/reservations")
    public ResponseEntity<?> createReservation(
        @RequestBody Reservation reservation) {
        try {
            Reservation persistedReservation = reservationService.addReservation(reservation);
            if (persistedReservation == null) {
                return ResponseEntity.badRequest().body("Error: Reservation could not be created. " +
                    "It is possible that the table is already reserved during the provided timeslot, " +
                    "or this customer may have the maximum amount of reservations already.");
            }
            return ResponseEntity.created(URI.create("/api/v1/reservations/" + persistedReservation.getReservationId())).body(persistedReservation);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Returns a customer, if one exists
     * @param customerId the customerId of the reservation
     * @return the customer, if it exists
     */
    @GetMapping("/api/v1/customers/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerId) {
        Customer customer = reservationService.getCustomer(customerId);
        if(customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(customer);
    }

    /**
     * Returns a list of the customer's reservations
     * @param customerId the customerId of the reservation
     * @return list of customer reservations
     */
    @GetMapping("/api/v1/customers/{customerId}/reservations")
    public ResponseEntity<Customer> getCustomerReservations(@PathVariable String customerId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

    /**
     * Returns a reservation, if one exists, with the provided reservationId
     * This uses the UUID rather than the sequential displayId!
     * @param reservationId the reservationId of the reservation
     * @return the reservation, if it exists
     */
    @GetMapping("/api/v1/reservations/{reservationId}")
    public ResponseEntity<Reservation> getReservation(@PathVariable String reservationId) {
        Reservation currentReservation = reservationService.getReservation(reservationId);
        if(currentReservation == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(Optional.of(currentReservation));
    }

    /**
     * Checks database to see if there are any reservations during the given
     * start and end time, in the restaurant with given ID.
     * @param restaurantId ID of restaurant to check.
     * @param start Start time of reservation.
     * @param end End time of reservation.
     * @return List of reservations during given time slot.
     */
    @GetMapping("/api/v1/restaurants/{restaurantId}/reservations")
    public ResponseEntity<List<Reservation>> getReservations(
            @PathVariable String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(reservationService.getReservationsDuringTimeSlot(restaurantId, start, end));
    }

    /**
     * Returns a restaurant with the given displayId
     * @param restaurantId the displayId of the restaurant
     * @return the restaurant, if it exists
     */
    @GetMapping("/api/v1/restaurants/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable long restaurantId) {
        Restaurant restaurant = reservationService.getRestaurant(restaurantId);
        if(restaurant == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(Optional.of(restaurant));
    }

    /**
     * Returns a reservation, if one exists, with the provided reservationId
     * This uses the sequential displayId rather than the UUID!
     * @param restaurantId the restaurantId of the restaurant
     * @param reservationId the reservationId of the reservation
     * @return the reservation, if it exists
     */
    @GetMapping("/api/v1/restaurants/{restaurantId}/reservations/{reservationId}")
    public ResponseEntity<Reservation> getReservation(@PathVariable long restaurantId, @PathVariable long reservationId) {
        Reservation currentReservation = reservationService.getReservation(reservationId);
        if(currentReservation == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(Optional.of(currentReservation));
    }



    public void query(QueryPlace queryParameters, String cursor) {
        //TODO - API Pull
    }

    public void dummyQuery(QueryPlace queryParameters) {
//        createDummyRestaurants();
    }

}
