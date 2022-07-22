package com.pastaebasta.eist.client.controller;

import com.google.maps.model.LatLng;
import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.Reservation;
import com.pastaebasta.eist.shared.model.Restaurant;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * ClientController
 * Executes requests from the client to the server.
 * <p>
 * Acts as the client Networking Layer, using Spring's WebClient
 *
 * @author Michael
 * @author Hisashi
 */
@RestController
public class ClientController {

    private final WebClient webClient;
    private final List<Reservation> customerReservations = new ArrayList<>();
    private final List<Restaurant> restaurants = new ArrayList<>();

    /**
     * Constructor
     * Initializes webClient with the base URL
     */
    public ClientController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * getCustomerById
     * GET Request
     * <p>
     * Gets customer by their ID
     *
     * @param id
     * @return
     */
    public Customer getCustomerById(String id) {
        return webClient.get()
                .uri("customers/" + id)
                .retrieve()
                .bodyToMono(Customer.class)
                .block();
    }

    /**
     * getCustomerByUsername
     * GET Request
     * <p>
     * Gets customer by username
     *
     * @param username
     * @return
     */
    public Customer getCustomerByUsername(String username) {
        return webClient.get()
                .uri("customers/username/" + username)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new RuntimeException("Customer with username '" + username + "' doesn't exist!")))
                .bodyToMono(Customer.class)
                .block();
    }

    /**
     * getCustomerByEmail
     * GET Request
     * <p>
     * Gets customer by their e-mail
     *
     * @param email
     * @return
     */
    public Customer getCustomerByEmail(String email) {
        return webClient.get()
                .uri("customers/email/" + email)
                .retrieve()
                .bodyToMono(Customer.class)
                .block();
    }

    /**
     * search
     * GET Request
     * <p>
     * Executes a search query, using the Google API. Returns a list of restaurants, which match the query.
     *
     * @param latLng              Geometric coordinates
     * @param name                Restaurant name
     * @param genre               Restaurant genre
     * @param minPriceCategory    Lowest price category
     * @param maxPriceCategory    Highest price category
     * @param distance            Maximum distance radius
     * @param restaurantsConsumer Consumer to be used to write returned list
     */
    public void search(LatLng latLng, String name, String genre, String minPriceCategory, String maxPriceCategory, int distance,
                       Consumer<List<Restaurant>> restaurantsConsumer) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("restaurants/search")
                        .queryParam("lat", latLng.lat)
                        .queryParam("lng", latLng.lng)
                        .queryParam("min_price_category", minPriceCategory)
                        .queryParam("max_price_category", maxPriceCategory)
                        .queryParam("distance", distance)
                        //.queryParam("name", name)
                        //.queryParam("genre", genre)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Restaurant>>() {
                })
                .onErrorStop()
                .subscribe(newRestaurants -> {
                    restaurants.clear();
                    restaurants.addAll(newRestaurants);
                    restaurantsConsumer.accept(restaurants);
                });
    }

    /**
     * getRestaurantReservations
     * GET Request
     * <p>
     * Gets all the reservations in the restaurant, with given id, between startLocalDateTime and endLocalDateTime.
     *
     * @param id                 Restaurant id
     * @param startLocalDateTime Time reservation starts
     * @param endLocalDateTime   Time reservation ends
     * @return List of reservations matching the query
     */
    public ArrayList<Reservation> getRestaurantReservations(String id, LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("reservations/restaurant/" + id)
                        .queryParam("start", DateTimeFormatter.ISO_DATE_TIME.format(
                                startLocalDateTime
                        ))
                        .queryParam("end", DateTimeFormatter.ISO_DATE_TIME.format(
                                endLocalDateTime
                        ))
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ArrayList<Reservation>>() {
                })
                .block();
    }

    /**
     * getCustomerReservations
     * GET Request
     * <p>
     * Gets all the reservations the customer (with given id) has.
     *
     * @param id
     * @param reservationsConsumer
     * @param active
     */
    public void getCustomerReservations(String id, Consumer<List<Reservation>> reservationsConsumer, boolean active) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("reservations/customer/" + id)
                        .queryParam("active", active)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Reservation>>() {
                })
                .onErrorStop()
                .subscribe(newReservations -> {
                    customerReservations.clear();
                    customerReservations.addAll(newReservations);
                    reservationsConsumer.accept(customerReservations);
                });
    }

    public Customer updateCustomer(Customer customer) {
        return webClient.put()
                .uri("customers/" + customer.getCustomerId())
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(Customer.class)
                .block();
    }

    /**
     * addCustomer
     * POST Request
     * <p>
     * Attempts to create a new customer in the database
     *
     * @param customer
     * @return
     */
    public Customer addCustomer(Customer customer) {
        return webClient.post()
                .uri("customers")
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(Customer.class)
                .block();
    }

    /**
     * addReservation
     * POST Request
     * <p>
     * Attempts to create a new reservation on the database.
     *
     * @param reservation
     * @return Reservation
     */
    public Reservation addReservation(Reservation reservation) {
        return webClient.post()
                .uri("reservations")
                .bodyValue(reservation)
                .retrieve()
                .bodyToMono(Reservation.class)
                .block();
    }

    /**
     * confirmReservation
     * PATCH Request
     * <p>
     * Attempts to confirm a reservation
     *
     * @param reservation
     */
    public void confirmReservation(Reservation reservation) {
        webClient.patch()
                .uri("reservations/" + reservation.getReservationId() + "/confirm");
    }

    /**
     * cancelReservation
     * DELETE Request
     * <p>
     * Attempts to cancel a reservation. This will DELETE the reservation permanently!
     *
     * @param reservation
     */
    public void cancelReservation(Reservation reservation) {
        webClient.delete()
                .uri("reservations/" + reservation.getReservationId() + "/cancel");
    }
}
