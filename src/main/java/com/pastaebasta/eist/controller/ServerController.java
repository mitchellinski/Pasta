package com.pastaebasta.eist.controller;

import com.pastaebasta.eist.model.*;
import com.pastaebasta.eist.service.ReservationService;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class ServerController {
    private ReservationService reservationService;
    public ServerController(){
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        reservationService = new ReservationService(customers,restaurants);
    }

    public String getReservationInformation(String restaurantId, long reservationId) {
        //TODO: Replace with JSON or other method later

        Optional<Restaurant> restaurant = reservationService.getRestaurantFromId(restaurantId);
        if(restaurant.isEmpty()) {
            return "No restaurant with id " + restaurantId + " was found!";
        }
        Optional<Reservation> currentReservation = reservationService.getReservationByRestaurant(restaurant.get(), reservationId);
        if(currentReservation.isEmpty()) {
            return "No reservation with id " + restaurantId + " was found!";
        }

        return currentReservation.get().getEmail() + " has confirmed a reservation at "
                + currentReservation.get().getStartDateTime() + " for " + currentReservation.get().getNumberOfVisitors()
                + "with id: " + currentReservation.get().getReservationId();
    }


    public void query(QueryPlace queryParameters, Cursor cursor) {
        //TODO - API Pull
    }

    public void dummyQuery(QueryPlace queryParameters) {
//        createDummyRestaurants();
    }

    private PriceCategory getRandomPriceCategory(int i) {
        i = i & 4;
        switch (i) {
            case 0:
                return PriceCategory.VERY_EXPENSIVE;
            case 1:
                return PriceCategory.EXPENSIVE;
            case 2:
                return PriceCategory.NORMAL;
            case 3:
                return PriceCategory.CHEAP;
        }
        return null;
    }

    public Optional<Reservation> createReservation(Customer customer, String restaurantID, LocalDateTime startTime, long tableId) {
        return reservationService.addReservation(restaurantID, customer, startTime, tableId);
    }

    public void createDummyRestaurants(String query) {
        for (int i = 0; i < 50; i++) {
            LocalDateTime startTime = LocalDateTime.of(2022, 05, 20, 18, 00, 00);
            LocalDateTime endTime = LocalDateTime.of(2022, 05, 20, 19, 00, 00);
            Restaurant restaurant = new Restaurant(
                    "restaurant_"+i,
                    "Name" + i,
                    "Address" + i,
                    query,
                    null,
                    3.3,
                    getRandomPriceCategory(i),
                    null,
                    startTime,
                    endTime,
                    new Point(i, i),
                    i & 10,
                    new ArrayList<DateTimeSlot>()
            );

            reservationService.addRestaurant(restaurant);
        }
    }
}
