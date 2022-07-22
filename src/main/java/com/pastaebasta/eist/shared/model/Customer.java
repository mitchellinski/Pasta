package com.pastaebasta.eist.shared.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;


/**
 * Customer - Data Model
 * Represents a single customer in the database.
 *
 * This class uses lombok to generate a constructor, getters and setters for the fields.
 */
@Document("customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String customerId;
    private String customerUsername;
    private String customerEmail;
    private String customerPassword;

    @DocumentReference
    private ArrayList<Reservation> reservationList;

    /**
     * Constructor
     * Initialize customer with no arguments
     */
    public Customer() {
    }

    /**
     * Constructor
     * Initializes customer with username, email, password
     * @param username customer username
     * @param email customer email
     * @param password customer password
     */
    public Customer(String username, String email, String password) {
        this.customerUsername = username;
        this.customerEmail = email;
        this.customerPassword = password;
    }

    /**
     * Constructor
     * Initializes customer with ID (used for testing)
     * @param customerId customer ID
     * @param username customer username
     * @param email customer email
     * @param password customer password
     */
    public Customer(String customerId, String username, String email, String password) {
        this.customerId = customerId;
        this.customerUsername = username;
        this.customerEmail = email;
        this.customerPassword = password;
        this.reservationList = new ArrayList<>();
    }

    /**
     * adds a reservation to the customer's list of reservations
     * @param reservation reservation to be added
     */
    public void addReservation(Reservation reservation) {
        this.reservationList.add(reservation);
    }

    /**
     * removes reservation from list
     * @param reservation reservation to be removed
     */
    public void deleteReservation(Reservation reservation) {
        this.reservationList.remove(reservation);
    }
}
