package com.pastaebasta.eist.testing.Database;

import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.Genre;
import com.pastaebasta.eist.shared.model.PriceCategory;
import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.server.repository.CustomerRepository;
import com.pastaebasta.eist.server.repository.RestaurantRepository;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.*;

/**
 * A class to seed/populate the database with data.
 * <p>
 * Disclaimer: this class should only be used for testing purposes.
 *
 * @version 1.0
 */
public class DatabaseSeeder {
    private static final int DEFAULT_NUMBER_OF_RESTAURANTS = 10;
    private static final int DEFAULT_NUMBER_OF_REVIEWS = 5;

    private static final List<Genre> GENRES = List.of(Genre.values());
    private static final List<String> ADDRESSES = List.of(
            "Sidbury Street", "Hilltop Street", "St Anne's Ridings",
            "Main Street", "York Street", "Goldcrest Limes");
    private static final List<String> REVIEWS = List.of(
            "This restaurant is great!", "I think this restaurant is ok.", "OldRestaurant was terrible.",
            "Not a good restaurant.", "This restaurant is terrible.", "This restaurant is awesome!",
            "Staff was great.", "Staff was terrible.", "Staff was ok.", "Staff was terrible.");
    private static final ArrayList<String> IMAGES = new ArrayList<>(List.of(
            "https://media.istockphoto.com/photos/modern-restaurant-interior-design-picture-id1211547141?k=20&m=1211547141&s=170667a&w=0&h=y__cQKe21f2M_IrBmCRdhxs3dCGfwSrVrQJSQMOeWAg=",
            "https://media.istockphoto.com/photos/pan-fried-duck-picture-id1081422898?k=20&m=1081422898&s=170667a&w=0&h=iCUEVuKoPANGSO6CECVkWFs5DGPMQVclTa4y7j5W-yE=",
            "https://media.istockphoto.com/photos/cozy-restaurant-for-gathering-with-friends-picture-id1159992039?k=20&m=1159992039&s=170667a&w=0&h=WCgDcn5ssR3HS0MnIzu5vRcD5_1z8qNLEk-Iq3EbhTs=",
            "https://media.istockphoto.com/photos/hands-view-of-young-people-eating-brunch-and-drinking-smoothies-bowl-picture-id1198032248?k=20&m=1198032248&s=170667a&w=0&h=QXaINccJXUcKmMbYe7VXF7DPHkl2XR3GOj9zrnjK2q4="
    ));

    private static final Random RNG = new Random();
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;

    // --------------------------------------------------------------------------------------------- //
    // Constructor
    public DatabaseSeeder(RestaurantRepository restaurantRepository, CustomerRepository customerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Seeds the database with randomly generated pseudo-data.
     */
    public ArrayList<Restaurant> seedRestaurants() {
        return seedRestaurants(DEFAULT_NUMBER_OF_RESTAURANTS);
    }

    /**
     * Seeds the database with randomly generated pseudo-data.
     *
     * @param numberOfRestaurants the number of restaurants to seed
     */
    public ArrayList<Restaurant> seedRestaurants(int numberOfRestaurants) {
        return seedRestaurants(numberOfRestaurants, DEFAULT_NUMBER_OF_REVIEWS);
    }

    /**
     * Seeds the database with randomly generated pseudo-data.
     *
     * @param numberOfRestaurants the number of restaurants to seed
     * @param numberOfReviews     the number of reviews to seed
     */
    public ArrayList<Restaurant> seedRestaurants(int numberOfRestaurants, int numberOfReviews) {
        ArrayList<Restaurant> restaurants = generateSimpleRestaurants(numberOfRestaurants, numberOfReviews);
        restaurantRepository.saveAll(restaurants);
        return restaurants;
    }

    // -------- Helper methods -------- //

    /**
     * Creates a list of Customers.
     *
     * @param numberOfCustomers the number of customers to create.
     * @return a list of customers.
     */
    public ArrayList<Customer> generateCustomers(int numberOfCustomers) {
        ArrayList<Customer> customers = new ArrayList<>();
        for (int i = 0; i < numberOfCustomers; i++) {
            customers.add(new Customer(UUID.randomUUID().toString(), "username" + i, "user" + i + "@gmail.com", new Argon2PasswordEncoder().encode("password" + i)));
        }
        return customers;
    }

    /**
     * Generates a list of restaurants with random data.
     *
     * @param numberOfRestaurants the number of restaurants to generate
     * @return A list of restaurants
     */
    private ArrayList<Restaurant> generateSimpleRestaurants(int numberOfRestaurants, int numberOfReviews) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < numberOfRestaurants; i++) {
            Restaurant restaurant = Restaurant.builder()
                    .restaurantId(UUID.randomUUID().toString())
                    .name("Restaurant " + i)
                    .build();
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    /**
     * Uses a RNG to pick a random price category from PriceCategory.
     *
     * @return a random price category
     */
    private PriceCategory getRandomPriceCategory() {
        return PriceCategory.values()[RNG.nextInt(PriceCategory.values().length)];
    }

    /**
     * Uses a RNG to pick a random restaurant genre from GENRES.
     *
     * @return a random restaurant genre
     */
    private Genre getRandomRestaurantGenre() {
        return GENRES.get(RNG.nextInt(GENRES.size()));
    }

    /**
     * Uses a RNG to pick a random address from ADDRESSES.
     *
     * @return a random address
     */
    private String getRandomAddress() {
        return ADDRESSES.get(RNG.nextInt(ADDRESSES.size()));
    }

    /**
     * Uses a RNG to pick a random review from REVIEWS.
     *
     * @return an ArrayList of random reviews of length numberOfReviews
     */
    private ArrayList<String> getRandomReviews(int numberOfReviews) {
        ArrayList<String> reviews = new ArrayList<>();
        for (int j = 0; j < numberOfReviews; j++) {
            reviews.add(REVIEWS.get(RNG.nextInt(REVIEWS.size())));
        }

        return reviews;
    }
}
