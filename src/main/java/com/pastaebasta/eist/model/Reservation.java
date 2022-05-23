package com.pastaebasta.eist.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Reservation {
    private long reservationId;
    private String email;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int numberOfVisitors;
    private static int id = 0;
    private boolean confirmationStatus;
    private long tableId;


    public Reservation(String email, LocalDateTime startDateTime, LocalDateTime endDateTime, int numberOfVisitors, long tableId){
        this.reservationId = id;
        id++;
        this.email = email;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.numberOfVisitors = numberOfVisitors;
        this.confirmationStatus = false;
        this.tableId = tableId;

    }

    public void confirmReservation(){
        confirmationStatus = true;
    }
    public void cancelReservation(){
        confirmationStatus = false;
    }



    //Getters
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public long getReservationId() {
        return reservationId;
    }

    public int getNumberOfVisitors() {
        return numberOfVisitors;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
}


