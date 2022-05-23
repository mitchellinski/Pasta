
package com.pastaebasta.eist.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pastaebasta.eist.model.Restaurant;
import com.pastaebasta.eist.service.ReservationService;

@RestController
@RequiredArgsConstructor
public class HelloController {


    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello World!";
    }


    @GetMapping("/v1/restaurants/{id}")
    public ResponseEntity<Long> getRestaurant(@PathVariable long id) {
        return ResponseEntity.ok(id);//ResponseEntity.ok(reservationService.getRestaurants().get());
    }

}
