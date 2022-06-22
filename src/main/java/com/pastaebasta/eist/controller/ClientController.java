package com.pastaebasta.eist.controller;

import com.pastaebasta.eist.model.Customer;
import com.pastaebasta.eist.model.Reservation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@RestController
public class ClientController {

    private final WebClient webClient;
    private final List<Reservation> customerReservations;
    private Customer customer;

    public ClientController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        customerReservations = new ArrayList<>();
    }

    public Customer getCustomer(String id) {
        /*RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(new URI("http://localhost:8080/api/v1/customers/1"), Customer.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }*/
        return webClient.get()
                .uri("customers/" + id)
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

    public void updateCustomer(String id) {

    }


}
