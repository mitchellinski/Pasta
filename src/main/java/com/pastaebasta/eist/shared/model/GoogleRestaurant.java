package com.pastaebasta.eist.shared.model;

import com.google.maps.model.LatLng;
import com.google.maps.model.OpeningHours;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Restaurant is a class that represents a restaurant.
 * It is used to store the information about a restaurant in the database.
 * <p>
 * This class uses lombok annotations to make it simpler/shorter. (https://projectlombok.org/)
 * If you can any questions, ask on discord.
 *
 * @version 1.0
 * @authors Hisashi, Ilja, Sebastian
 */
@Document("restaurants")
@Data
@Builder
@Jacksonized
public class GoogleRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String restaurantId;

    private String name;
    private String address;
    private Genre restaurantGenre;
    private LatLng coordinates;

    private PriceCategory priceCategory;
    private double ratingAverage;

    private int numberOfTables;
    private OpeningHours openingHours;

    //TODO: Import PlacePhotos and replace
    private String[] pictures;

    public GoogleRestaurant(String restaurantId, String name, String address, Genre restaurantGenre, LatLng coordinates
            , PriceCategory priceCategory, double ratingAverage, int numberOfTables, OpeningHours openingHours
            , String[] pictures) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.restaurantGenre = restaurantGenre;
        this.coordinates = coordinates;
        this.priceCategory = priceCategory;
        this.ratingAverage = ratingAverage;
        this.numberOfTables = numberOfTables;
        this.openingHours = openingHours;
        this.pictures = pictures;
    }
}
