package com.pastaebasta.eist.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.maps.model.LatLng;
import com.google.maps.model.OpeningHours;
import com.google.maps.model.Photo;
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
    @JsonIgnore
    private LatLng coordinates;
    private double ratingAverage;

    @JsonIgnore
    private OpeningHours openingHours;

    //TODO: Import PlacePhotos and replace
    @JsonIgnore
    private Photo[] pictures;

    public GoogleRestaurant(String restaurantId, String name, String address, LatLng coordinates
            , double ratingAverage, OpeningHours openingHours, Photo[] pictures) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.coordinates = coordinates;
        this.ratingAverage = ratingAverage;
        this.openingHours = openingHours;
        this.pictures = pictures;
    }
}
