package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.DateTimeSlot;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.server.repository.CustomerRepository;
import com.pastaebasta.eist.server.repository.DateTimeSlotRepository;
import com.pastaebasta.eist.server.repository.ReservationRepository;
import com.pastaebasta.eist.server.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * PBReservationService
 * General service to handle reservations.
 */
@Service
@RequiredArgsConstructor
public class PBReservationService {
    private final DateTimeSlotRepository dateTimeSlotRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    /**
     * Gets a customer from the database, if one exists, otherwise creates a new customer.
     * UNSAFE - Testing purposes only
     *
     * @param customerId The customer's id
     * @return The customer
     */
    public Customer getCustomer_FORCE_CREATE(String customerId) {
        return customerRepository.findById(customerId).orElseGet(() -> {
            // THIS IS FOR TESTING PURPOSES ONLY!
            // UNSECURE WAY TO CREATE A CUSTOMER
            System.err.println(">>>> NOOOOOOOO");
            return customerRepository.save(
                    new Customer(
                            customerId,
                            "user_" + customerId,
                            null,
                            "password"
                    )
            );
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
        return customerRepository.findById(customerId).orElse(null);
    }

    public boolean isTableFree(String restaurantId, LocalDateTime start, LocalDateTime end, long tableId) {
        Reservation reservation = reservationRepository.getByRestaurantIdAndTableIdAndStartDateTimeAndEndDateTime(restaurantId, tableId, start, end);
        return reservation == null;
    }

    public Reservation addReservation(Reservation reservation) throws IllegalArgumentException {
        assert (reservation.getReservationId() != null);
        assert (reservation.getRestaurantId() != null);
        assert (reservation.getCustomerId() != null);
        assert (reservation.getStartDateTime() != null);
        assert (reservation.getEndDateTime() != null);

        // Check if the table is free
        if (!isTableFree(reservation.getRestaurantId(), reservation.getStartDateTime(), reservation.getEndDateTime(), reservation.getTableId())) {
            return null;
        }

        Customer customer = getCustomer_FORCE_CREATE(reservation.getCustomerId());
        if (customer.getReservationList().size() >= 3) {
            return null;
        }

        customer.addReservation(reservation);
        customerRepository.save(customer);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsDuringTimeSlot(String restaurantId, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.getByRestaurantIdAndStartDateTimeAndEndDateTime(restaurantId, start, end);
    }

    public List<DateTimeSlot> getDateTimeSlots(String restaurantId) {
        return dateTimeSlotRepository.getAllByRestaurantId(restaurantId);
    }

    public Reservation getReservation(String reservationId) {
        return reservationRepository.getByReservationId(reservationId);
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
