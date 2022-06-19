package com.pastaebasta.eist.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    private String customerId;
    private String customerUsername;
    private String customerEmail;

    @DocumentReference
    private ArrayList<Reservation> reservationList;

    public Customer(String customerId, String customerUsername, String customerEmail) {
        this.customerId = customerId;
        this.customerUsername = customerUsername;
        this.customerEmail = customerEmail;
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
}
