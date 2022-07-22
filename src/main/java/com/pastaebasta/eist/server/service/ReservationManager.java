package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Reservation Manager
 * The reservation manager handles the high level creation and management of reservations
 *
 * @author Hisashi
 */
@Service
@RequiredArgsConstructor
public class ReservationManager {
    private static final int MAXIMUM_RESERVATION_AMOUNT_PER_CUSTOMER = 3;
    private static final int RESERVATION_CHECK_CYCLE_MINUTES = 5;

    private final ReservationService reservationService;
    private final CustomerService customerService;

    /**
     * Checks to see if the table is free
     * @param restaurantId
     * @param start
     * @param end
     * @param tableId
     * @return
     */
    public boolean isTableFree(String restaurantId, LocalDateTime start, LocalDateTime end, long tableId) {
        Reservation reservation = reservationService.getByRestaurantIdAndTableIdAndStartDateTimeAndEndDateTime(restaurantId, tableId, start, end);
        return reservation == null;
    }


    /**
     * Confirms reservation
     * @param reservation Reservation to be confirmed
     */
    public void confirmReservation(Reservation reservation) {
        reservation.setConfirmed(true);
        System.out.println("status:" + reservation);
        reservationService.save(reservation);
    }

    /**
     * Cancels reservation, deletes it from all databases!
     * @param reservation Reservation to be cancelled
     * @return true if cancelled, false otherwise
     */
    public boolean cancelReservation(Reservation reservation) {
        if (reservation.isConfirmed()) {
            return false;
        }

        reservationService.deleteById(reservation.getReservationId());

        Customer customer = customerService.getCustomerById(reservation.getCustomerId());
        if (customer != null) {
            customer.deleteReservation(reservation);
            customerService.saveCustomer(customer);
        }

        return true;
    }

    /**
     * Cancels all reservations less than 12 hours ahead
     * Scheduled: Runs every x minutes (based on RESERVATION_CHECK_CYCLE_MINUTES)
     */
    @Scheduled(fixedRate = RESERVATION_CHECK_CYCLE_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void notifyPendingReservations() {
        List<Reservation> pendingReservations = reservationService.getReservationsBefore(
            LocalDateTime.now().plusHours(12).minusSeconds(5)
        );

        pendingReservations.forEach(reservation -> {
            if (!reservation.isConfirmed()) {
                cancelReservation(reservation);
            }
        });
    }

    /**
     * Attempts to create a new reservation in the database, asserting it and then
     * handling the high level operations necessary to add it to the customer and reservation repositories
     * @param reservation Reservation to be created
     * @return
     * @throws IllegalArgumentException
     */
    public Reservation createReservation(Reservation reservation) throws IllegalArgumentException {
        assert(reservation.getReservationId() != null);
        assert(reservation.getRestaurantId() != null);
        assert(reservation.getCustomerId() != null);
        assert(reservation.getStartDateTime() != null);
        assert(reservation.getEndDateTime() != null);

        if (!isTableFree(reservation.getRestaurantId(), reservation.getStartDateTime(), reservation.getEndDateTime(), reservation.getTableId())) {
            System.out.println("Table not free");
            return null;
        }

        Customer customer = customerService.getCustomerById(reservation.getCustomerId());
        if (customer.getReservationList().size() >= MAXIMUM_RESERVATION_AMOUNT_PER_CUSTOMER) {
            System.out.println("Too many reservations");
            return null;
        }

        Reservation persisted = reservationService.save(reservation);
        customer.addReservation(persisted);
        customerService.saveCustomer(customer);
        return persisted;
    }


    /**
     * Gets a list of reservations between two specified DateTimes
     * @param restaurantId id
     * @param start start datetime
     * @param end end datetime
     * @return List of Reservations
     */
    public List<Reservation> getReservationsDuringTimeSlot(String restaurantId, LocalDateTime start, LocalDateTime end) {
        return reservationService.getByRestaurantIdAndStartDateTimeAndEndDateTime(restaurantId, start, end);
    }
}
