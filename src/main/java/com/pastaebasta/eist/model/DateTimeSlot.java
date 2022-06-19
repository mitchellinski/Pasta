package com.pastaebasta.eist.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * DateTimeSlot
 * OUTDATED: Please do not use DateTimeSlots, as they are redundant when working with a database!
 */
@Document("date_time_slots")
@Data
@AllArgsConstructor
public class DateTimeSlot {
    @Id
    private final String dateTimeSlotId;
    @DocumentReference
    private ArrayList<Reservation> reservations;

    private String restaurantId;
    private LocalDateTime dateTime;

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
    }
    public void removeReservation(Reservation reservation){
        reservations.remove(reservation);
    }
}
