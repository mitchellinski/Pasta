package com.pastaebasta.eist.server.controller;

import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.server.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private ReservationService reservationService;

    /**
     * Create new reservation.
     * Checks DB to see if reservation slot and table are available.
     * If created, return persisted instance.
     * @param reservation Reservation to be added
     * @return Persisted reservation instance.
     */
    @PostMapping
    public ResponseEntity<?> createReservation(
            @RequestBody Reservation reservation) {
        try {
            Reservation persistedReservation = reservationService.saveReservation(reservation);
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

    @PutMapping("/confirm{reservationId}")
    public ResponseEntity<?> confirmReservation(@PathVariable String id) {
        try {
            Reservation reservation = reservationService.getReservationById(id);
            if (reservation == null) {
                return ResponseEntity.badRequest().body("No reservations with the provided ID were found.");
            }
            reservationService.confirmReservation(reservation);

            return ResponseEntity.ok("yes worked!!!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable String id) {
        try {
            Reservation reservation = reservationService.getReservationById(id);
            if (reservation == null) {
                return ResponseEntity.badRequest().body("No reservations with the provided ID were found.");
            }
            reservationService.cancelReservation(reservation);

            return ResponseEntity.ok("yes worked!!!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Returns a list of the customer's reservations
     * @param customerId the customerId of the reservation
     * @return list of customer reservations
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getReservationsByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

    /**
     * Returns a reservation, if one exists, with the provided reservationId
     * This uses the UUID rather than the sequential displayId!
     * @param reservationId the reservationId of the reservation
     * @return the reservation, if it exists
     */
    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String reservationId) {
        Reservation currentReservation = reservationService.getReservationById(reservationId);
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
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Reservation>> getReservationsByRestaurantId(
            @PathVariable String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(reservationService.getReservationsDuringTimeSlot(restaurantId, start, end));
    }


}
