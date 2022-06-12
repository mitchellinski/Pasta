package com.pastaebasta.eist.repository;

import com.pastaebasta.eist.model.Restaurant;
import com.pastaebasta.eist.model.SimpleRestaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<SimpleRestaurant, String> {

	@Query("{name:'?0'}")
	SimpleRestaurant findItemByName(String name);

	@Query(value="{restaurantGenre:'?0'}", fields="{'name' : 1, 'address' : 1, 'restaurantGenre' : 1, 'ratingAverage' : 1, 'priceCategory' : 1, 'numberOfTables' : 1}")
	List<SimpleRestaurant> findAll(String category);

	public long count();

}