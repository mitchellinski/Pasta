package com.pastaebasta.eist;

import com.pastaebasta.eist.controller.ServerController;
import com.pastaebasta.eist.model.Customer;
import com.pastaebasta.eist.model.Reservation;
import com.pastaebasta.eist.model.Restaurant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

public class FoundationTests {
	@Test
	public void testCreateReservation() {
		ServerController serverController = new ServerController(); // Create Controller
		serverController.createDummyRestaurants("hi?"); // Create restaurants

		// Create Customer
		Customer dummyCustomer = new Customer("pastaebastaEIST@gmail.com");
		LocalDateTime startTime = LocalDateTime.of(2022, 5, 20, 18, 0, 0);

		// Attempt to create a new reservation for dummyCustomer at restaurant 0
		Optional<Reservation> newReservation = serverController.createReservation(dummyCustomer, "restaurant_0", startTime, 1234);
		if(newReservation.isPresent()) {
			System.out.println("Reservation " + newReservation.get().getReservationId() + " was created!");
			// Attempt to retrieve the reservation
			System.out.println("Retrieved: " + dummyCustomer.getReservation(newReservation.get().getReservationId()));
		} else {
			System.out.println("Oops, something went wrong.");
		}

		// Redundant, but formalized version of above check
		assertNotEquals(Optional.empty(), newReservation, "No reservations were created");
		assertEquals(
			newReservation.get(),
			dummyCustomer.getReservation(newReservation.get().getReservationId()),
			"Reservation on customer does not match expected reservation!"
		);

		//LocalDateTime endTime = LocalDateTime.of(2022, 5, 20, 19, 0, 0);

		/*Reservation dummyReservation = new Reservation(
			"pasteebastaEIST@gmail.com",
			startTime,
			endTime,
			4,
			1234
		);
		 */


        //System.out.println(serverController.getReservationInformation(dummyCustomer, 1234));
	}
}
