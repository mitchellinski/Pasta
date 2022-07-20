package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.shared.model.Customer;
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
        reservation.setConfirmationStatus(true);
        reservationRepository.save(reservation);
    }

    public void cancelReservation(Reservation reservation) {
        reservationRepository.deleteById(reservation.getReservationId());
    }

    public List<Reservation> getReservationsDuringTimeSlot(String restaurantId, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.getByRestaurantIdAndStartDateTimeAndEndDateTime(restaurantId, start, end);
    }

    public Reservation getReservationById(String reservationId) {
        return reservationRepository.getByReservationId(reservationId);
    }

    public List<Reservation> getReservationsByCustomerId(String customerId) {
        return reservationRepository.getAllByCustomerId(customerId);
    }
}
