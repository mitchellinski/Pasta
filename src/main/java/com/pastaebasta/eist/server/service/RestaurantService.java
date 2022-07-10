package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.shared.model.Restaurant;
import com.pastaebasta.eist.server.repository.RestaurantRepository;
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
