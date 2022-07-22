package com.pastaebasta.eist.server.controller;

import com.pastaebasta.eist.server.service.ReservationManager;
import com.pastaebasta.eist.server.service.ReservationService;
import com.pastaebasta.eist.shared.model.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Reservation Controller
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
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private ReservationService reservationService;
    private ReservationManager reservationManager;

    /**
     * Create new reservation.
     * Checks DB to see if reservation slot and table are available.
     * If created, return persisted instance.
     *
     * @param reservation Reservation to be added
     * @return Persisted reservation instance.
     */
    @PostMapping
    public ResponseEntity<?> createReservation(
            @RequestBody Reservation reservation) {
        try {
            Reservation persistedReservation = reservationManager.createReservation(reservation);
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
     * Confirm a reservation
     * Sets reservation confirmed status to true
     *
     * @param reservationId Reservation ID
     * @return Response Code OK, if updated, BAD_REQUEST otherwise
     */
    @PatchMapping("/{reservationId}/confirm")
    public ResponseEntity<?> confirmReservation(@PathVariable String reservationId) {
        try {
            Reservation reservation = reservationService.getReservationById(reservationId);
            if (reservation == null) {
                return ResponseEntity.badRequest().body("No reservations with the provided ID were found.");
            }
            reservationManager.confirmReservation(reservation);

            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Cancel a reservation
     * DELETES a reservation from the database.
     * @param reservationId Reservation ID
     * @return Response Code OK, if updated, BAD_REQUEST otherwise
     */
    @DeleteMapping("/{reservationId}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable String reservationId) {
        try {
            Reservation reservation = reservationService.getReservationById(reservationId);
            if (reservation == null) {
                return ResponseEntity.badRequest().body("No reservations with the provided ID were found.");
            }

            boolean cancelled = reservationManager.cancelReservation(reservation);
            return cancelled ? ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Returns a list of all reservations a customer has
     *
     * @param customerId the customerId of the reservation
     * @return the customer, if it exists
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Reservation>> getCustomerReservations(@PathVariable String customerId, @RequestParam(required = false) boolean active) {
        List<Reservation> reservationList = reservationService.getReservationsByCustomerId(customerId);
        if (active) {
            reservationList = reservationList.stream().filter(r -> r.getEndDateTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        } else {
            reservationList = reservationList.stream().filter(r -> r.getEndDateTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        }
        System.out.println("rs: " + reservationList);
        return ResponseEntity.ok().body(reservationList);
    }

    /**
     * Returns a reservation, if one exists, with the provided reservationId
     * This uses the UUID rather than the sequential displayId!
     *
     * @param reservationId the reservationId of the reservation
     * @return the reservation, if it exists
     */
    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String reservationId) {
        Reservation currentReservation = reservationService.getReservationById(reservationId);
        if (currentReservation == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(Optional.of(currentReservation));
    }

    /**
     * Checks database to see if there are any reservations during the given
     * start and end time, in the restaurant with given ID.
     *
     * @param restaurantId ID of restaurant to check.
     * @param start        Start time of reservation.
     * @param end          End time of reservation.
     * @return List of reservations during given time slot.
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Reservation>> getReservationsByRestaurantId(
            @PathVariable String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(reservationService.getReservationsDuringTimeSlot(restaurantId, start, end));
    }


}
