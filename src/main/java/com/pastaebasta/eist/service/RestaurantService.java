package com.pastaebasta.eist.service;

import com.pastaebasta.eist.model.Restaurant;
import com.pastaebasta.eist.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant getRestaurantById(String restaurantId) {
        return restaurantRepository.getByRestaurantId(restaurantId);
    }

}
