package com.pastaebasta.eist.shared.model;

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

    private String name;
    private String address;
    private Genre restaurantGenre;
    private String coordinates;

    private PriceCategory priceCategory;
    private double ratingAverage;

    private int numberOfTables;
    private int openingTime;
    private int closingTime;

    private ArrayList<String> pictures;

}
