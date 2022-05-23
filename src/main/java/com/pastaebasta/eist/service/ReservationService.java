package com.pastaebasta.eist.service;

import com.pastaebasta.eist.model.Customer;
import com.pastaebasta.eist.model.DateTimeSlot;
import com.pastaebasta.eist.model.Reservation;
import com.pastaebasta.eist.model.Restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private List<Customer> customers;
    private List<Restaurant> restaurants;

    public ReservationService(ArrayList customers, ArrayList restaurants){
        this.customers = customers;
        this.restaurants = restaurants;
    }
    public void addRestaurant(Restaurant restaurant){
        restaurants.add(restaurant);
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

    public List<Customer> getCustomerByEmail(String email){
        //TODO
        return null;
    }

    public Customer getCustomerById(long id){
        //TODO
        return null;
    }
    public void addReservation(long restaurantId, Customer customer, LocalDateTime startTime, long tableId){
        //find Restaurant
        for(Restaurant restaurant : restaurants){
            if(restaurant.getRestaurantId() != restaurantId){
                continue;
            }

            // TODO: Actually check to see if slot is busy
            // public DateTimeSlot(int year, int month, int dayOfMonth, int hour, int minute){
            //        reservations = new ArrayList<Reservation>();
            //        dateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
            //    }
            Reservation reservation = new Reservation(

                    customer.getEmail(),
                    startTime,
                    startTime,
                    69,
                    tableId
            );

            //restaurant.getReservationByTimeslot(new DateTimeSlot(startTime), 1337); // this depends on what we are currently discussing
            // here
        }
    }
    public List<Reservation> getReservationByCustomer(Customer customer){
        //TODO
        return null;
    }
    public List<Reservation> getReservationByRestaurant(Restaurant restaurant){
        //TODO
        return null;
    }
    public Reservation getReservationById(long id){
        //TODO
        return null;
    }

    //getters
    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
    public List<Customer> getCustomers() {
        return customers;
    }


}
