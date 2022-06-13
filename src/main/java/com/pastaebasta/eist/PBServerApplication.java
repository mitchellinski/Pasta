package com.pastaebasta.eist;

import com.pastaebasta.eist.model.DateTimeSlot;
import com.pastaebasta.eist.model.PriceCategory;
import com.pastaebasta.eist.model.Restaurant;
import com.pastaebasta.eist.model.SimpleRestaurant;
import com.pastaebasta.eist.repository.RestaurantRepository;
import com.pastaebasta.eist.testing.Database.DatabaseSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * The PBServerApplication starts the Spring modules, which will auto-wire components of the project.
 */
@SpringBootApplication
@EnableMongoRepositories
public class PBServerApplication implements CommandLineRunner {

	@Autowired
	RestaurantRepository restaurantRepository;

	public static void main(String[] args) {
		System.out.println("""
								
																						   88
													  ,d                                   88                                    ,d
													  88                                   88                                    88
				8b,dPPYba,   ,adPPYYba,  ,adPPYba,  MM88MMM  ,adPPYYba,      ,adPPYba,     88,dPPYba,   ,adPPYYba,  ,adPPYba,  MM88MMM  ,adPPYYba,
				88P'    "8a  ""     `Y8  I8[    ""    88     ""     `Y8     a8P_____88     88P'    "8a  ""     `Y8  I8[    ""    88     ""     `Y8
				88       d8  ,adPPPPP88   `"Y8ba,     88     ,adPPPPP88     8PP""\"""\""     88       d8  ,adPPPPP88   `"Y8ba,     88     ,adPPPPP88
				88b,   ,a8"  88,    ,88  aa    ]8I    88,    88,    ,88     "8b,   ,aa     88b,   ,a8"  88,    ,88  aa    ]8I    88,    88,    ,88
				88`YbbdP"'   `"8bbdP"Y8  `"YbbdP"'    "Y888  `"8bbdP"Y8      `"Ybbd8"'     8Y"Ybbd8"'   `"8bbdP"Y8  `"YbbdP"'    "Y888  `"8bbdP"Y8
				88
				88
				
				> Pasta e Basta - Application Server
				
				
				""");
		SpringApplication.run(PBServerApplication.class, args);
	}

	//CREATE
	public ArrayList<SimpleRestaurant> createGroceryItems() {
		System.out.println("Data creation started...");
		ArrayList<SimpleRestaurant> restaurants = new DatabaseSeeder(restaurantRepository).seed();
		System.out.println("Data creation complete...");
		return restaurants;
	}

	// READ
	// 1. Show all the data
	public void showAllGroceryItems() {

		restaurantRepository.findAll().forEach(item -> System.out.println(getItemDetails(item)));
	}

	// 2. Get item by name
	public void getGroceryItemByName(String name) {
		System.out.println("Getting item by name: " + name);
		SimpleRestaurant item = restaurantRepository.findItemByName(name);
		System.out.println(getItemDetails(item));
	}

	// 3. Get name and quantity of a all items of a particular category
	public void getItemsByCategory(String category) {
		System.out.println("Getting items for the category " + category);
		List<SimpleRestaurant> list = restaurantRepository.findAll(category);

		list.forEach(item -> System.out.println("Name: " + item.getName() + ", Address: " + item.getAddress()));
	}

	// 4. Get count of documents in the collection
	public void findCountOfGroceryItems() {
		long count = restaurantRepository.count();
		System.out.println("Number of documents in the collection: " + count);
	}

	// Print details in readable form

	public String getItemDetails(SimpleRestaurant item) {
		StringBuilder b = new StringBuilder();
		b.append("Id: " + item.getRestaurantId() + "\n")
		.append("Name: " + item.getName() + "\n")
		.append("Address: " + item.getAddress() + "\n")
		.append("Category: " + item.getRestaurantGenre() + "\n")
		.append("Price Category: " + item.getPriceCategory() + "\n")
		.append("Reviews: " + item.getReviewsString() + "\n");



		return b.toString();
	}
	public void updateCategoryName(String category, String newGenre) {

		// Change to this new value

		// Find all the items with the category snacks
		List<SimpleRestaurant> list = restaurantRepository.findAll(category);

		list.forEach(item -> {
			// Update the category in each document
			item.setRestaurantGenre(newGenre);
		});

		// Save all the items in database
		List<SimpleRestaurant> itemsUpdated = restaurantRepository.saveAll(list);

		if(itemsUpdated != null)
			System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
	}

	// DELETE
	public void deleteGroceryItem(String id) {
		restaurantRepository.deleteById(id);
		System.out.println("Item with id " + id + " deleted...");
	}

	public void run(String... args) {
		//restaurantRepository.deleteAll();
		if (restaurantRepository.count() == 0) {
			createGroceryItems();
		}
		showAllGroceryItems();

	}

}
