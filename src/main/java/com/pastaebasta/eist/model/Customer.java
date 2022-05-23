package com.pastaebasta.eist.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private static long customerCount = 0;
    private long customerId;
    private String email;
    private ArrayList<Reservation> reservationList;

    public Customer(String email, ArrayList<Reservation> customerReservations) {
        this.customerId = customerCount;
        customerCount++;
        this.email = email;
        this.reservationList = customerReservations;
    }

    public Customer(String email) {
        this.customerId = customerCount;
        customerCount++;
        this.email = email;
        this.reservationList = new ArrayList<>();
    }


    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(ArrayList<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public void addReservation(Reservation reservation) {
        this.reservationList.add(reservation);
    }

    public Reservation getReservation(long id) {
        for (Reservation reservation : reservationList) {
            if (reservation.getReservationId() == id) {
                return reservation;
            }
        }
        return null;
    }
}
