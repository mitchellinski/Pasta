package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.server.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerService customerService;

    public boolean isTableFree(String restaurantId, LocalDateTime start, LocalDateTime end, long tableId) {
        Reservation reservation = reservationRepository.getByRestaurantIdAndTableIdAndStartDateTimeAndEndDateTime(restaurantId, tableId, start, end);
        System.out.println("Found " + reservation);
        return reservation == null;
    }


    public void confirmReservation(Reservation reservation) {
        reservation.setConfirmed(true);
        reservationRepository.save(reservation);
    }

    public boolean cancelReservation(Reservation reservation) {
        if (reservation.isConfirmed()) {
            return false;
        }
        reservationRepository.deleteById(reservation.getReservationId());
        return true;
    }

    public List<Reservation> getReservationsDuringTimeSlot(String restaurantId, LocalDateTime start, LocalDateTime end) {
        System.out.println(restaurantId);
        System.out.println(start);
        System.out.println(end);
        List<Reservation> reservations = reservationRepository.getByRestaurantIdAndStartDateTimeAndEndDateTime(restaurantId, start, end);
        System.out.println(reservations);
        return reservations;
    }

    public Reservation getReservationById(String reservationId) {
        return reservationRepository.getByReservationId(reservationId);
    }

    public List<Reservation> getReservationsByCustomerId(String customerId) {
        return reservationRepository.getAllByCustomerId(customerId);
    }
}
