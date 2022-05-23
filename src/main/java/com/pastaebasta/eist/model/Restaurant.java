package com.pastaebasta.eist.model;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant {
    private static long restaurantId = 0;
    private String name;
    private String address;
    private String type;
    private ArrayList<Image> pictures;
    private double ratingAverage;
    private PriceCategory priceCategory;
    private ArrayList<String> reviews;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private Point coordinates;
    private int numberOfTables;
    private ArrayList<DateTimeSlot> restaurantTimeSlots;
    private HashMap<Integer, ArrayList<DateTimeSlot>> reservationMap;

    public Restaurant(long restaurantId, String name, String address, String type, ArrayList<Image> pictures,
                      double ratingAverage, PriceCategory priceCategory, ArrayList<String> reviews, LocalDateTime openingTime,
                      LocalDateTime closingTime, Point coordinates, int numberOfTables,
                      ArrayList<DateTimeSlot> restaurantTimeSlots) {
        restaurantId++;
        this.name = name;
        this.address = address;
        this.type = type;
        this.pictures = pictures;
        this.ratingAverage = ratingAverage;
        this.priceCategory = priceCategory;
        this.reviews = reviews;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.coordinates = coordinates;
        this.numberOfTables = numberOfTables;
        this.restaurantTimeSlots = restaurantTimeSlots;
    }


    /*
    Queries API for all relevant information about a specific restaurant
     */
    public static Restaurant getRestaurantInformation() {
        //TODO
        return null;
    }

    public void addReservation() {

    }


    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Image> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Image> pictures) {
        this.pictures = pictures;
    }

    public double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(int ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public int getNumberOfTables() {
        return numberOfTables;
    }

    public void setNumberOfTables(int numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public ArrayList<DateTimeSlot> getRestaurantTimeSlots() {
        return restaurantTimeSlots;
    }

    public void setRestaurantTimeSlots(ArrayList<DateTimeSlot> restaurantTimeSlots) {
        this.restaurantTimeSlots = restaurantTimeSlots;
    }

    /*
    Gets all reservations in a specific timeslot for the given restaurant.
    */
    public ArrayList<Restaurant> getReservationsByTimeSlot(DateTimeSlot timeSlot) {
        //TODO
        return null;
    }

    /*
    Finds a reservation by a specific Timeslot and id
     */
    public Reservation getReservationByTimeslot(DateTimeSlot timeSlot, long id) {
        //TODO
        return null;
    }

    /*
    Finds a table from a specific DateTimeSlot/TableID mapping
     */
    public Table getTableByTimeslotAndNumber(DateTimeSlot timeSlot, int tableNumber) {
        //TODO
        return null;
    }


}
