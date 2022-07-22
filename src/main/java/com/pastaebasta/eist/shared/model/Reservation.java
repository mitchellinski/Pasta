package com.pastaebasta.eist.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

/**
 * Reservation - Data Model
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
    private String restaurantName;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private int numberOfVisitors;
    private boolean confirmed;
    private long tableId;

    public Reservation(String customerId, String restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime,
                       int numberOfVisitors, long tableId) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.numberOfVisitors = numberOfVisitors;
        this.confirmed = false;
        this.tableId = tableId;
    }
}


