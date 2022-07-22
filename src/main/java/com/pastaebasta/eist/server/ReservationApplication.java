package com.pastaebasta.eist.server;

import com.pastaebasta.eist.server.repository.CustomerRepository;
import com.pastaebasta.eist.server.repository.ReservationRepository;
import com.pastaebasta.eist.server.repository.RestaurantRepository;
import com.pastaebasta.eist.server.service.ReservationManager;
import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.testing.Database.DatabaseSeeder;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


/**
 * Pasta e Basta - EIST 2022
 *
 * Reservation Application
 * The Reservation Application starts the Spring modules on the server side,
 * which will auto-wire components of the project.
 *
 * @author Hisashi
 */
@SpringBootApplication
@EnableMongoRepositories
@AllArgsConstructor
@EnableScheduling
public class ReservationApplication implements CommandLineRunner {
	ReservationManager reservationService;
	RestaurantRepository restaurantRepository;
	CustomerRepository customerRepository;
	ReservationRepository reservationRepository;

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
		SpringApplication.run(ReservationApplication.class, args);
		System.out.println("------------------------------------------------------");
		System.out.println("> Server: Running");
	}

	/**
	 * WARNING: do NOT use in production
	 * WARNING: this method will DELETE ALL entries in the database
	 *
	 * Database testing method, used to clear and repopulate database.
	 */
	private void test() {
		customerRepository.deleteAll();
		reservationRepository.deleteAll();

		DatabaseSeeder seeder = new DatabaseSeeder(restaurantRepository, customerRepository);
		ArrayList<Restaurant> restaurants = seeder.seedRestaurants(1, 10);

//		ArrayList<Customer> customers = new ArrayList<>(List.of(new Customer("1", "myUsername", "e@mail.com", "123")));
		ArrayList<Customer> customers = seeder.generateCustomers(1);
		customerRepository.saveAll(customers);

		LocalDateTime startDateTime = LocalDateTime.of(2023, 1, 1, 10, 0);
		LocalDateTime endDateTime = LocalDateTime.of(2023, 1, 1, 12, 0);

		Random random = new Random();

		customers.stream().findAny().ifPresent(customer -> {
			for (int i = 0; i < 5 ; i++) {
				int restaurantId = random.nextInt(restaurants.size());
				Reservation newReservation = Reservation.builder()
						.reservationId(UUID.randomUUID().toString())
						.customerId(customer.getCustomerId())
						.startDateTime(startDateTime)
						.endDateTime(endDateTime)
						//.tableId(1337L)
						.restaurantId(restaurants.get(restaurantId).getRestaurantId())
						.build();
				customer.addReservation(newReservation);
				reservationRepository.save(newReservation);
			}

		});

		customers.stream().peek(customer -> {
			for (int i = 0; i < random.nextInt(5) ; i++) {
				int restaurantId = random.nextInt(restaurants.size());
				Reservation newReservation = Reservation.builder()
						.reservationId(UUID.randomUUID().toString())
						.customerId(customer.getCustomerId())
						.startDateTime(LocalDateTime.now())
						.endDateTime(LocalDateTime.now().plusHours(1))
						//.tableId(1337L)
						.restaurantId(restaurants.get(restaurantId).getRestaurantId())
						.build();
				customer.addReservation(newReservation);
				reservationRepository.save(newReservation);
			}
		}).forEach(customerRepository::save);

		System.out.println("------------------------------------------------------");
		List<Reservation> r = reservationService.getReservationsDuringTimeSlot(
				restaurants.get(0).getRestaurantId(),
				startDateTime,
				endDateTime);
		for (Reservation reservation : r) {
			System.out.println(reservation);
		}
		System.out.println(customers.get(0));
	}

	public void run(String... args) {
	}
}
