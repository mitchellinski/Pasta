package com.pastaebasta.eist.shared.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Reservation Data Model
 * Represents a reservation in the database
 *
 * This class uses lombok to generate a constructor, getters and setters for the fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reservationId;

    private String customerId;
    private String restaurantId;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private int numberOfVisitors;
    private boolean confirmationStatus;
    private long tableId;

    public void confirmReservation(){
        confirmationStatus = true;
    }

    public void cancelReservation(){
        confirmationStatus = false;
    }

    public Reservation(String customerId, String restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime,
                       int numberOfVisitors, long tableId) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.numberOfVisitors = numberOfVisitors;
        this.confirmationStatus = false;
        this.tableId = tableId;
    }
}


