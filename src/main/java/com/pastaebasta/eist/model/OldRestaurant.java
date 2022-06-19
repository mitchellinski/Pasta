package com.pastaebasta.eist.model;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * OUTDATED!
 * Please use Restaurant instead of OldRestaurant.
 *
 * This class is outdated and should not be used anymore.
 */
public class OldRestaurant {
    private static long restaurantCount = 0;

    private final String restaurantId;

    private String name;
    private String address;
    private String restaurantGenre;
    private ArrayList<Image> pictures;
    private double ratingAverage;
    private PriceCategory priceCategory;
    private ArrayList<String> reviews;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private Point coordinates;
    private int numberOfTables;
    private ArrayList<DateTimeSlot> restaurantTimeSlots;
    private HashMap<LocalDateTime, Reservation> reservationMap;


    public OldRestaurant(String restaurantId, String name, String address, String type, ArrayList<Image> pictures,
                         double ratingAverage, PriceCategory priceCategory, ArrayList<String> reviews, LocalDateTime openingTime,
                         LocalDateTime closingTime, Point coordinates, int numberOfTables,
                         ArrayList<DateTimeSlot> restaurantTimeSlots) {
        super();
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.restaurantGenre = type;
        this.pictures = pictures;
        this.ratingAverage = ratingAverage;
        this.priceCategory = priceCategory;
        this.reviews = reviews;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.coordinates = coordinates;
        this.numberOfTables = numberOfTables;
        this.restaurantTimeSlots = restaurantTimeSlots;

        // TODO: database access
        this.reservationMap = new HashMap<>();
    }



    /*
    Queries API for all relevant information about a specific restaurant
     */
    public static OldRestaurant getRestaurantInformation() {
        //TODO
        return null;
    }

    /**
     * TEMPORARY FUNCTION
     * @param reservation
     * @param localDateTime
     * @return
     */
    public Optional<Reservation> addReservation(Reservation reservation, LocalDateTime localDateTime) {
        return Optional.ofNullable(reservationMap.computeIfAbsent(localDateTime, x->reservation));
    }

    /**
     * TEMPORARY FUNCTION
     * @param localDateTime
     * @return
     */
    public Optional<Reservation> getReservation(LocalDateTime localDateTime) {
        return Optional.ofNullable(reservationMap.get(localDateTime));
    }

    public Optional<Reservation> getReservationById(long id) {
        for (Map.Entry<LocalDateTime, Reservation> entry : reservationMap.entrySet()) {
            if (Objects.equals(id, entry.getValue().getReservationId())) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    public void setRestaurantGenre(String genre){
        this.restaurantGenre = genre;
    }

    public String getRestaurantId() {
        return restaurantId;
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

    public String getRestaurantGenre() {
        return restaurantGenre;
    }

    public void setType(String type) {
        this.restaurantGenre = type;
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
    public ArrayList<OldRestaurant> getReservationsByTimeSlot(DateTimeSlot timeSlot) {
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

    /**
     * TODO Proper checking! This is a temporary function
     * @param localDateTime
     * @return
     */
    public boolean isTimeSlotEmpty(LocalDateTime localDateTime) {
        Reservation reservation = this.reservationMap.get(localDateTime);
        return reservation == null;
    }

    /*
    Finds a table from a specific DateTimeSlot/TableID mapping
     */
    public Table getTableByTimeslotAndNumber(DateTimeSlot timeSlot, int tableNumber) {
        //TODO
        return null;
    }

    @Override
    public String toString() {
        return "OldRestaurant{" +
                "restaurantId=" + restaurantId + '\n' +
                ", name='" + name + '\n' +
                ", address='" + address + '\n' +
                ", restaurantGenre='" + restaurantGenre + '\n' +
//                ", pictures=" + pictures +
                ", ratingAverage=" + ratingAverage + '\n' +
                ", priceCategory=" + priceCategory + '\n' +
//                ", reviews=" + reviews +
//                ", openingTime=" + openingTime +
//                ", closingTime=" + closingTime +
//                ", coordinates=" + coordinates +
//                ", numberOfTables=" + numberOfTables +
//                ", restaurantTimeSlots=" + restaurantTimeSlots +
//                ", reservationMap=" + reservationMap +
                '}' + '\n' + '\n';
    }
}
