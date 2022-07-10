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
        return reservation == null;
    }

    public Reservation saveReservation(Reservation reservation) throws IllegalArgumentException {
        assert (reservation.getReservationId() != null);
        assert (reservation.getRestaurantId() != null);
        assert (reservation.getCustomerId() != null);
        assert (reservation.getStartDateTime() != null);
        assert (reservation.getEndDateTime() != null);

        // Check if the table is free
        if (!isTableFree(reservation.getRestaurantId(), reservation.getStartDateTime(), reservation.getEndDateTime(), reservation.getTableId())) {
            return null;
        }

        Customer customer = customerService.getCustomer_FORCE_CREATE(reservation.getCustomerId());
        if (customer.getReservationList().size() >= 3) {
            return null;
        }

        customer.addReservation(reservation);
        customerService.saveCustomer(customer);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsDuringTimeSlot(String restaurantId, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.getByRestaurantIdAndStartDateTimeAndEndDateTime(restaurantId, start, end);
    }

    public Reservation getReservationById(String reservationId) {
        return reservationRepository.getByReservationId(reservationId);
    }
}
