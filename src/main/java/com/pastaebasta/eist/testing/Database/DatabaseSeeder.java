package com.pastaebasta.eist.testing.Database;

import com.pastaebasta.eist.model.PriceCategory;
import com.pastaebasta.eist.model.SimpleRestaurant;
import com.pastaebasta.eist.repository.RestaurantRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A class to seed the database with data.
 *
 * Disclaimer: this class should only be used for testing purposes.
 *
 * @version 1.0
 */
public class DatabaseSeeder {
	private static final int DEFAULT_NUMBER_OF_RESTAURANTS = 10;
	private static final int DEFAULT_NUMBER_OF_REVIEWS = 5;

	private static final List<String> GENRES = List.of("Italian", "Chinese", "Japanese", "French", "American", "Mexican", "Thai", "Indian", "Korean", "German", "Spanish");
	private static final List<String> ADDRESSES = List.of("Sidbury Street", "Hilltop Street", "St Anne's Ridings", "Main Street", "York Street", "Goldcrest Limes");
	private static final List<String> REVIEWS = List.of("This restaurant is great!", "This restaurant is ok.", "This restaurant is terrible.");

	private static final Random RNG = new Random();


	private final RestaurantRepository restaurantRepository;

	public DatabaseSeeder(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}


	/**
	 * Seeds the database with data.
	 */
	public ArrayList<SimpleRestaurant> seed() {
		return seed(DEFAULT_NUMBER_OF_RESTAURANTS);
	}

	/**
	 * Seeds the database with data.
	 *
	 * @param numberOfRestaurants the number of restaurants to seed
	 */
	public ArrayList<SimpleRestaurant> seed(int numberOfRestaurants){
		return seed(numberOfRestaurants, DEFAULT_NUMBER_OF_REVIEWS);
	}

	/**
	 * Seeds the database with data.
	 *
	 * @param numberOfRestaurants the number of restaurants to seed
	 * @param numberOfReviews the number of reviews to seed
	 */
	public ArrayList<SimpleRestaurant> seed(int numberOfRestaurants, int numberOfReviews){
		ArrayList<SimpleRestaurant> restaurants = new ArrayList<>();
		LocalDateTime startTime = LocalDateTime.of(2022, 5, 20, 18, 0, 0);
		LocalDateTime endTime = LocalDateTime.of(2022, 5, 20, 19, 0, 0);

		for (int i = 0; i<numberOfRestaurants; i++) {
			SimpleRestaurant restaurant = new SimpleRestaurant(
				UUID.randomUUID().toString(),
				"Generic Restaurant Name "+i,
				getRandomAddress() + " " + i,
				getRandomRestaurantGenre(),
				RNG.nextInt(0,11),
				getRandomPriceCategory(),
				RNG.nextInt(5,15),
				getRandomReviews(numberOfReviews),
				startTime,
				endTime
			);
			restaurants.add(restaurant);
			restaurantRepository.save(restaurant);
		}

		return restaurants;
	}


	// -------- Helper methods -------- //
	private PriceCategory getRandomPriceCategory() {
		List<PriceCategory> priceCategories = Arrays.asList(PriceCategory.values());
		return priceCategories.get(RNG.nextInt(priceCategories.size()));
	}

	private String getRandomRestaurantGenre() {
		return GENRES.get(RNG.nextInt(GENRES.size()));
	}

	private String getRandomAddress() {
		return ADDRESSES.get(RNG.nextInt(ADDRESSES.size()));
	}

	private ArrayList<String> getRandomReviews(int numberOfReviews) {
		ArrayList<String> reviews = new ArrayList<>();
		for (int j = 0; j < numberOfReviews; j++) {
			reviews.add(REVIEWS.get(RNG.nextInt(REVIEWS.size())));
		}

		return reviews;
	}

}
