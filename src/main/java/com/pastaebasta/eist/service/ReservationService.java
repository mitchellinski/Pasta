package com.pastaebasta.eist.service;

import com.pastaebasta.eist.model.Customer;
import com.pastaebasta.eist.model.DateTimeSlot;
import com.pastaebasta.eist.model.Reservation;
import com.pastaebasta.eist.model.Restaurant;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Restaurant> getRestaurantFromId(long restaurantId) {
        for(Restaurant restaurant : restaurants){
            if(restaurant.getRestaurantId() == restaurantId){
                return Optional.of(restaurant);
            }
        }

        return Optional.empty();
    }

    public Optional<Reservation> addReservation(long restaurantId, Customer customer, LocalDateTime startTime, long tableId){
        Optional<Restaurant> restaurant = getRestaurantFromId(restaurantId);
        if(restaurant.isEmpty()) {
            System.err.println("There is no restaurant with id "+ restaurantId);
            return Optional.empty();
        }

        // TODO: Implement proper verification of timeslot availability considering tables!!
        if (!restaurant.get().isTimeSlotEmpty(startTime)) {
            System.out.println("This timeslot is not empty!");
            return Optional.empty();
        }

        Reservation newReservation = new Reservation(
            customer.getEmail(),
            startTime,
            startTime,
            69,
            tableId
        );

        Optional<Reservation> createdReservation = restaurant.get().addReservation(newReservation, startTime);
        createdReservation.ifPresent(customer::addReservation);
        return createdReservation;
    }
    public List<Reservation> getReservationByCustomer(Customer customer){
        //TODO
        return null;
    }
    public Optional<Reservation> getReservationByRestaurant(Restaurant restaurant, long id){
        return restaurant.getReservationById(id);
    }

    /**
     * TODO: temporary
     * @param id
     * @return
     */
    public Optional<Reservation> getReservationById(long id){
        return Optional.empty();
    }

    //getters
    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
    public List<Customer> getCustomers() {
        return customers;
    }


}
