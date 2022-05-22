/*
package hello.world.demo;


import lombok.RequiredArgsConstructor;
import model.Restaurant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import service.ReservationService;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final ReservationService reservationService;


    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello World!";
    }


    @GetMapping("/v1/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable long id) {
        return null;//ResponseEntity.ok(reservationService.getRestaurants().get());
    }

    @GetMapping("/v1/restaurants/{id}/tables/{id}")
    public ResponseEntity<Restaurant> getTable(@PathVariable long restaurantId, @PathVariable long tableId) {
        return null;
    }
}
*/
