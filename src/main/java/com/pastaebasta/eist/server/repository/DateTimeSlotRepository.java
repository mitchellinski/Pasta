package com.pastaebasta.eist.server.repository;

import com.pastaebasta.eist.shared.model.DateTimeSlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OUTDATED: Please DO NOT use this class or DateTimeSlots, as they are redundant for the Database!
 * DateTimeSlotRepository
 *
 * This class has been kept here for legacy reasons.
 */
public interface DateTimeSlotRepository extends MongoRepository<DateTimeSlot, String> {

	@Query("{dateTime:?0}")
	DateTimeSlot findByDateTime(LocalDateTime dateTime);

	@Query("{dateTimeSlotId:'?0'}")
	DateTimeSlot findByDateTimeSlotId(String dateTimeSlotId);

	@Query(value="{dateTimeSlotId:'?0'}", fields="{dateTimeSlotId:1, dateTime:1, reservations:1}")
	List<DateTimeSlot> findAll(String id);

	@Query("{restaurantId:'?0'}")
	DateTimeSlot findByRestaurantId(String restaurantId);

	@Query(value="{restaurantId:'?0'}", fields="{dateTimeSlotId:1, dateTime:1, reservations:1, restaurantId:1}")
	List<DateTimeSlot> getAllByRestaurantId(String restaurantId);
}
