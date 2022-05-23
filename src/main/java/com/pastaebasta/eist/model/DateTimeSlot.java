package com.pastaebasta.eist.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DateTimeSlot {

    private LocalDateTime dateTime;
    private ArrayList<Reservation> reservations;

    public DateTimeSlot(int year, int month, int dayOfMonth, int hour, int minute){
        reservations = new ArrayList<Reservation>();
        dateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

    public DateTimeSlot(LocalDateTime localDateTime) {
        this.dateTime = localDateTime;

    }

    public List<Reservation> getReservations(){
        return reservations;
    }

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation){
        reservations.remove(reservation);
    }

}
