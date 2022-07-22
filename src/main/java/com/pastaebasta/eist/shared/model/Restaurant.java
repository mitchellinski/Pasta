package com.pastaebasta.eist.shared.model;

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
 *
 * This class uses lombok annotations to make it simpler/shorter. (https://projectlombok.org/)
 * If you can any questions, ask on discord.
 *
 * @version 1.0
 * @author Hisashi
 * @author Ilja
 * @author Sebastian
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

    /**
     * Turns ID into an integer by using its returned hashcode
     * Usually used as seed for pseudo RNG.
     * @param id
     * @return
     */
    public static int IdToInteger(String id) {
        return Math.abs(id.hashCode());
    }

    /**
     * Get a simple "hash", used for consistently creating
     * the interior map of a restaurant.
     * @return
     */
    public int getPseudoHash() {
        return "".equals(restaurantId) ? 0 : IdToInteger(restaurantId);
    }
}
