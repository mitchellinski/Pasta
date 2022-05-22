package controller;

import model.*;
import service.ReservationService;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ServerController {
    private ReservationService reservationService;
    public ServerController(){
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        reservationService = new ReservationService(customers,restaurants);
    }


    public String getReservationInformation(Customer customer, int reservationId) {
        //TODO: Replace with JSON or other method later

        Reservation currentReservation = reservationService.getReservationById(reservationId);

        return currentReservation.getEmail() + " has confirmed a reservation at "
                + currentReservation.getStartDateTime() + " for " + currentReservation.getNumberOfVisitors()
                + "with id: " + currentReservation.getReservationId();


    }


    public void query(QueryPlace queryParameters, Cursor cursor) {
        //TODO - API Pull
    }

    public void dummyQuery(QueryPlace queryParameters) {
        createDummyRestaurants();
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

    public void createReservation(Customer customer, long restaurantID, LocalDateTime startTime, long tableId) {
        reservationService.addReservation(restaurantID, customer, startTime, tableId);
    }

    public void createDummyRestaurants() {
        for (int i = 0; i < 50; i++) {
            LocalDateTime startTime = LocalDateTime.of(2022, 05, 20, 18, 00, 00);
            LocalDateTime endTime = LocalDateTime.of(2022, 05, 20, 19, 00, 00);
            Restaurant restaurant = new Restaurant(
                    i,
                    "Name" + i,
                    "Address" + i,
                    "Italian",
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
