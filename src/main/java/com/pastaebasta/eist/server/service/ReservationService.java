package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.server.repository.ReservationRepository;
import com.pastaebasta.eist.shared.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * SERVICE - Reservation Service
 *
 * Handles high-level actions on Reservation:
 * find, get, add, delete, update, etc
 */
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsDuringTimeSlot(String restaurantId, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.getByRestaurantIdAndStartDateTimeAndEndDateTime(restaurantId, start, end);
    }

    public Reservation getByRestaurantIdAndTableIdAndStartDateTimeAndEndDateTime(String restaurantId, long tableId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return reservationRepository.getByRestaurantIdAndTableIdAndStartDateTimeAndEndDateTime(restaurantId, tableId, startDateTime, endDateTime);
    }

    public List<Reservation> getByRestaurantIdAndStartDateTimeAndEndDateTime(String restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime){
        return reservationRepository.getByRestaurantIdAndStartDateTimeAndEndDateTime(restaurantId, startDateTime, endDateTime);
    }

    public Reservation getReservationById(String reservationId) {
        return reservationRepository.getByReservationId(reservationId);
    }

    public List<Reservation> getReservationsByCustomerId(String customerId) {
        return reservationRepository.getAllByCustomerId(customerId);
    }

    public void deleteById(String id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getReservationsBefore(LocalDateTime localDateTime) {
        return reservationRepository.getReservationsBefore(localDateTime);
    }
}
