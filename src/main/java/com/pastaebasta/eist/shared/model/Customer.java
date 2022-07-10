package com.pastaebasta.eist.shared.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;


/**
 * Customer Data Model
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

    public Customer() {
    }

    public Customer(String username, String email, String password) {
        this.customerUsername = username;
        this.customerEmail = email;
        this.customerPassword = password;
    }

    public Customer(String customerId, String username, String email, String password) {
        this.customerId = customerId;
        this.customerUsername = username;
        this.customerEmail = email;
        this.customerPassword = password;
        this.reservationList = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        this.reservationList.add(reservation);
    }

    public Reservation getReservation(String id) {
        for (Reservation reservation : reservationList) {
            if (id.equals(reservation.getReservationId())) {
                return reservation;
            }
        }
        return null;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }
}
