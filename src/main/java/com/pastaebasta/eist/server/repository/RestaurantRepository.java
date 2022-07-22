package com.pastaebasta.eist.server.repository;

import com.pastaebasta.eist.shared.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * RestaurantRepository
 * Repository for the Restaurant Data Model
 *
 * This class uses Spring Data MongoDB to generate a repository for the Restaurant Data Model.
 * @author Hisashi
 */
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
	@Query("{name:'?0'}")
	Restaurant getByName(String name);

	@Query("{restaurantId:'?0'}")
	Restaurant getByRestaurantId(String restaurantId);

	@Query("{displayId:?0}")
	Restaurant getByRestaurantId(long restaurantId);

	@Query(value="{restaurantGenre:'?0'}", fields="{name:1, address:1, restaurantGenre:1, pictures:1, ratingAverage:1, priceCategory:1, reviews:1, openingTime:1, closingTime:1, coordinates:1, numberOfTables:1, restaurantTimeSlots:1}")
	List<Restaurant> getAll(String category);
}