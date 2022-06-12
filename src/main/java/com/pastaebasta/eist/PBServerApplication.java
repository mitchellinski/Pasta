package com.pastaebasta.eist;

import com.pastaebasta.eist.model.DateTimeSlot;
import com.pastaebasta.eist.model.PriceCategory;
import com.pastaebasta.eist.model.Restaurant;
import com.pastaebasta.eist.model.SimpleRestaurant;
import com.pastaebasta.eist.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * The PBServerApplication starts the Spring modules, which will auto-wire components of the project.
 */
@SpringBootApplication
@EnableMongoRepositories
public class PBServerApplication implements CommandLineRunner {

	@Autowired
	RestaurantRepository restaurantRepository;

	public static void main(String[] args) {
		SpringApplication.run(PBServerApplication.class, args);
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
				
				> Pasta e Basta - Server Application successfully started.
				
				
				""");
	}

	//CREATE
	void createGroceryItems() {
		System.out.println("Data creation started...");
		LocalDateTime startTime = LocalDateTime.of(2022, 05, 20, 18, 00, 00);
		LocalDateTime endTime = LocalDateTime.of(2022, 05, 20, 19, 00, 00);

		for (int i = 0; i<10; i++) {
			restaurantRepository.save(
				new SimpleRestaurant("restaurant_"+i, "Nice Restaurant Name "+i, "Musterstraße " + i, "Italian", 5.0,
						PriceCategory.EXPENSIVE,  2)
			);
		}


		System.out.println("Data creation complete...");
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

		System.out.println(
				"Item Name: " + item.getName() +
						", \nAddress: " + item.getAddress() +
						", \nType: " + item.getRestaurantGenre()
		);

		return "";
	}
	public void updateCategoryName(String category) {

		// Change to this new value
		String newGenre = "Chinese";

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

		System.out.println("-------------CREATE GROCERY ITEMS-------------------------------\n");

		createGroceryItems();

		System.out.println("\n----------------SHOW ALL GROCERY ITEMS---------------------------\n");

		showAllGroceryItems();

		System.out.println("\n--------------GET ITEM BY NAME-----------------------------------\n");

		getGroceryItemByName("Nice Restaurant Name 3");

		System.out.println("\n-----------GET ITEMS BY CATEGORY---------------------------------\n");

		getItemsByCategory("Italian");

		System.out.println("\n-----------UPDATE CATEGORY NAME OF SNACKS CATEGORY----------------\n");

		updateCategoryName("Chinese");

		System.out.println("\n----------DELETE A GROCERY ITEM----------------------------------\n");

		deleteGroceryItem("restaurant_1");

		System.out.println("\n------------FINAL COUNT OF GROCERY ITEMS-------------------------\n");

		findCountOfGroceryItems();

		System.out.println("\n-------------------THANK YOU---------------------------");

	}

}
