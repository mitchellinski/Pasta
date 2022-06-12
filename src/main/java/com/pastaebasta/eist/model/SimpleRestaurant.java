package com.pastaebasta.eist.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Document("restaurants")
public class SimpleRestaurant {
	@Id
	private final String restaurantId;

	private String name;
	private String address;
	private String restaurantGenre;
	private double ratingAverage;
	private PriceCategory priceCategory;
	private int numberOfTables;

	public SimpleRestaurant(String restaurantId, String name, String address, String restaurantGenre, double ratingAverage, PriceCategory priceCategory, int numberOfTables) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.address = address;
		this.restaurantGenre = restaurantGenre;
		this.ratingAverage = ratingAverage;
		this.priceCategory = priceCategory;
		this.numberOfTables = numberOfTables;
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
}
