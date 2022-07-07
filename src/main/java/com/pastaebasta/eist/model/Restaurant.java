package com.pastaebasta.eist.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Restaurant is a class that represents a restaurant.
 * It is used to store the information about a restaurant in the database.
 *
 * This class uses lombok annotations to make it simpler/shorter. (https://projectlombok.org/)
 * If you can any questions, ask on discord.
 *
 * @authors Hisashi, Ilja, Sebastian
 * @version 1.0
 */
@Document("restaurants")
@Data @Builder
public class Restaurant {

	@Id
	private final String restaurantId;

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
