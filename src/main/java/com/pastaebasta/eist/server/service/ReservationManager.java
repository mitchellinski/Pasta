package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.DateTimeSlot;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.server.repository.CustomerRepository;
import com.pastaebasta.eist.server.repository.ReservationRepository;
import com.pastaebasta.eist.server.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * PASTA E BASTA - Reservation Manager
 * The reservation manager handles the high level creation and management of reservations
 */
@Service
@RequiredArgsConstructor
public class ReservationManager {
    private static final int MAXIMUM_RESERVATION_AMOUNT_PER_CUSTOMER = 3;

    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    private final CustomerService customerService;
    private final ReservationService reservationService;

    @Scheduled(fixedRate = 5000)
    public void notifyPendingReservations() {
        var pendingReservations = reservationRepository.getReservationsBetween(
            LocalDateTime.now().plusHours(12).minusSeconds(5),
            LocalDateTime.now().plusHours(12).plusSeconds(5)
        );

        pendingReservations.forEach(reservation -> {
            if (!reservation.isConfirmationStatus()) {
                reservationService.cancelReservation(reservation);
            }
        });
    }

    /**
     * Gets a customer from the database, if one exists, otherwise creates a new customer.
     * UNSAFE - Testing purposes only
     *
     * @param customerId The customer's id
     * @return The customer
     */
    public Customer getCustomer(String customerId) {
        return customerService.getCustomerById(customerId);
    }

    public Reservation addReservation(Reservation reservation) throws IllegalArgumentException {
        assert (reservation.getReservationId() != null);
        assert (reservation.getRestaurantId() != null);
        assert (reservation.getCustomerId() != null);
        assert (reservation.getStartDateTime() != null);
        assert (reservation.getEndDateTime() != null);

        if (!reservationService.isTableFree(reservation.getRestaurantId(), reservation.getStartDateTime(), reservation.getEndDateTime(), reservation.getTableId())) {
            return null; // Table not free
        }

        Customer customer = customerService.getCustomer_FORCE_CREATE(reservation.getCustomerId());
        if (customer.getReservationList().size() >= MAXIMUM_RESERVATION_AMOUNT_PER_CUSTOMER) {
            return null; // Customer has too many reservations
        }

        customer.addReservation(reservation);
        customerRepository.save(customer);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsDuringTimeSlot(String restaurantId, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.getByRestaurantIdAndStartDateTimeAndEndDateTime(restaurantId, start, end);
    }

    public Reservation getReservation(String reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    public Reservation getReservation(long reservationId) {
        return reservationRepository.getByReservationId(reservationId);
    }

    public Restaurant getRestaurant(String restaurantId) {
        return restaurantRepository.getByRestaurantId(restaurantId);
    }

    public Restaurant getRestaurant(long restaurantId) {
        return restaurantRepository.getByRestaurantId(restaurantId);
    }

    //TODO delete before push
    public Customer saveCustomer(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    public Customer findCustomerByUsername(String username) {
        return customerRepository.getItemByName(username).orElse(null);
    }

    public Iterable<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
}
