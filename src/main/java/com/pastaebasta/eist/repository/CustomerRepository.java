package com.pastaebasta.eist.repository;

import com.pastaebasta.eist.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * CustomerRepository
 * Repository for the Customer Data Model
 *
 * This class uses Spring Data MongoDB to generate a repository for the Customer Data Model.
 * @author Hisashi
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

	@Query("{customerUsername:'?0'}")
	Optional<Customer> getItemByName(String name);

	@Query("{customerId:'?0'}")
	@Override
	Optional<Customer> findById(String id);

	@Query(value="{customerId:'?0'}", fields="{customerId:1, customerUsername:1, customerEmail:1, reservationList:1}")
	List<Customer> getAll(String id);

}