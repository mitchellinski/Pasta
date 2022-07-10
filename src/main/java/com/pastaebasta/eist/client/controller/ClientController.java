package com.pastaebasta.eist.client.controller;

import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.shared.model.Reservation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RestController
public class ClientController {

    private final WebClient webClient;
    private final List<Reservation> customerReservations;

    public ClientController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        customerReservations = new ArrayList<>();
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

    public void getCustomerReservations(String id, Consumer<List<Reservation>> reservationsConsumer) {
        webClient.get()
                .uri("customers/" + id + "/reservations")
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

    public void addCustomer(Customer customer) {
        webClient.post()
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
}
