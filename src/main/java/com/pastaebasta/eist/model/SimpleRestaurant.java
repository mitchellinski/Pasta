package com.pastaebasta.eist.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Document("simple_restaurants")
@AllArgsConstructor
public class SimpleRestaurant {
	@Id
	private final String restaurantId;

	private String name;
	private String address;
	private String restaurantGenre;
	private double ratingAverage;
	private PriceCategory priceCategory;
	private int numberOfTables;
	private ArrayList<String> reviews;

	private LocalDateTime openingTime;
	private LocalDateTime closingTime;


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

	public void setRestaurantGenre(String restaurantGenre) {
		this.restaurantGenre = restaurantGenre;
	}

	public double getRatingAverage() {
		return ratingAverage;
	}

	public void setRatingAverage(double ratingAverage) {
		this.ratingAverage = ratingAverage;
	}

	public PriceCategory getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(PriceCategory priceCategory) {
		this.priceCategory = priceCategory;
	}

	public int getNumberOfTables() {
		return numberOfTables;
	}

	public void setNumberOfTables(int numberOfTables) {
		this.numberOfTables = numberOfTables;
	}

	public String getReviewsString() {
		return String.join(",", reviews);
	}
}
