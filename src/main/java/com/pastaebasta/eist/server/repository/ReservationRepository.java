package com.pastaebasta.eist.server.repository;

import com.pastaebasta.eist.shared.model.Reservation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ReservationRepository
 * Repository for the Reservation Data Model
 *
 * This class uses Spring Data MongoDB to generate a repository for the Reservation Data Model.
 * @author Hisashi
 */
public interface ReservationRepository extends PagingAndSortingRepository<Reservation, String> {
	@Query("{email:'?0'}")
	Reservation getByEmail(String email);

	@Query("{'restaurantId' : '?0','startDateTime' : {$gte : ?1}, 'endDateTime' : {$lte : ?2}}")
	List<Reservation> getByRestaurantIdAndStartDateTimeAndEndDateTime(String restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime);

	@Query("{'restaurantId' : '?0', 'tableId': ?1, 'startDateTime' : {$gte : ?2}, 'endDateTime' : {$lte : ?3}}")
	Reservation getByRestaurantIdAndTableIdAndStartDateTimeAndEndDateTime(String restaurantId, long tableId, LocalDateTime startDateTime, LocalDateTime endDateTime);

	@Query("{reservationId:'?0'}")
	Reservation getByReservationId(String reservationId);

	@Query("{displayId:?0}")
	Reservation getByReservationId(long reservationId);

	@Query("{customerId:'?0'}")
	List<Reservation> getAllByCustomerId(String customerId);

	@Query("{restaurantId:'?0'}")
	List<Reservation> getAllByRestaurantId(String restaurantId);

	@Query(value="{email:'?0'}")
	List<Reservation> getAllWithEmail(String email);

}