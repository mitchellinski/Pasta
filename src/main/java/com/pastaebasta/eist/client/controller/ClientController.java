package com.pastaebasta.eist.client.controller;

import com.pastaebasta.eist.shared.model.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RestController
public class ClientController {

    private final WebClient webClient;
    private final List<Reservation> customerReservations = new ArrayList<>();
    private final List<Restaurant> restaurants = new ArrayList<>();

    public ClientController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Customer getCustomerById(String id) {
        return webClient.get()
                .uri("customers/" + id)
                .retrieve()
                .bodyToMono(Customer.class)
                .block();
    }


    public Customer getCustomerByUsername(String username) {
        return webClient.get()
                .uri("customers/username/" + username)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new RuntimeException("Customer with username '" + username + "' doesn't exist!")))
                .bodyToMono(Customer.class)
                .block();
    }

    public Customer getCustomerByEmail(String email) {
        return webClient.get()
                .uri("customers/email/" + email)
                .retrieve()
                .bodyToMono(Customer.class)
                .block();
    }

    public void search(String query, Genre genre, PriceCategory priceCategory, long distance, Consumer<List<Restaurant>> restaurantsConsumer) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("restaurants/test_search")
                    .queryParam("query", query)
                    .queryParam("genre", genre)
                    .queryParam("price_category", priceCategory)
                    .queryParam("distance", distance)
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

    public void getCustomerReservations(String id, Consumer<List<Reservation>> reservationsConsumer) {
        webClient.get()
                .uri("reservations/customer/" + id)
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

    public void updateCustomer(Customer customer) {

    }

    public Customer addCustomer(Customer customer) {
        return webClient.post()
            .uri("customers")
            .bodyValue(customer)
            .retrieve()
            .bodyToMono(Customer.class)
            .block();
    }

    public void customerUsernameTaken(String username) {
        webClient.get()
                .uri("customers/username/" + username)
                .retrieve()
                .onStatus(HttpStatus::is2xxSuccessful, response -> Mono.error(new RuntimeException("Username already taken")));
    }

    public void customerEmailTaken(String email) {
        webClient.get()
                .uri("customers/email/" + email)
                .retrieve()
                .onStatus(HttpStatus::is2xxSuccessful, response -> Mono.error(new RuntimeException("Email already taken")));
    }

    public Reservation addReservation(Reservation reservation) {
        return webClient.post()
            .uri("reservations")
            .bodyValue(reservation)
            .retrieve()
            .bodyToMono(Reservation.class)
            .block();
    }
}
