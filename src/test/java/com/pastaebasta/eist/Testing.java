package com.pastaebasta.eist;

import com.pastaebasta.eist.shared.model.PriceCategory;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.testing.Database.DatabaseSeeder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Testing {
	@Test
	public void testReservationBuilder() {
		for(int i = 0; i<50; i++) {
			Reservation r = Reservation.builder()
				.reservationId("id_"+i)
				.tableId(i)
				.build();
			assertEquals("id_"+i, r.getReservationId());
			assertEquals(i, r.getTableId());
		}
	}

	@Test
	public void testRestaurantBuilder() {
		for(int i = 0; i<50; i++) {
			PriceCategory pc = DatabaseSeeder.getRandomPriceCategory();
			Restaurant r = Restaurant.builder()
				.restaurantId("id_"+i)
				.address("test_road_"+i)
				.priceCategory(pc)
				.build();
			assertEquals("id_"+i, r.getRestaurantId());
			assertEquals("test_road_"+i, r.getAddress());
			assertEquals(pc, r.getPriceCategory());
		}
	}
}
