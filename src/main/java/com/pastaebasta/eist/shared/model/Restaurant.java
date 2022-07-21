package com.pastaebasta.eist.shared.model;

import com.google.maps.model.LatLng;
import com.google.maps.model.OpeningHours;
import com.google.maps.model.Photo;
import com.google.maps.model.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;

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
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String restaurantId;

    // Google Attributes
    private OpeningHours openingHours;
    private LatLng coordinates;
    private Photo[] pictures;
    private double ratingAverage;
    private String name;
    private String address;
    private String restaurantGenre;

    //private Genre restaurantGenre;
    private PriceCategory priceCategory;

    private int numberOfTables;
    private int openingTime;
    private int closingTime;

    private double distance;

    public static int IdToInteger(String id) {
        return Math.abs(id.hashCode());
    }

    public int getPseudoHash(String id) {
        return "".equals(restaurantId) ? 0 : IdToInteger(id);
    }

    public int getPseudoHash() {
        return "".equals(restaurantId) ? 0 : IdToInteger(restaurantId);
    }
}
