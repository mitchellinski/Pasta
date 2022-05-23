package com.pastaebasta.eist.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private static long customerCount = 0;
    private long customerId;
    private String email;
    private List<Reservation> reservationList;

    public Customer(String email, ArrayList<Reservation> customerReservations) {
        this.customerId = customerCount;
        customerCount++;
        this.email = email;
        this.reservationList = customerReservations;
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

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
