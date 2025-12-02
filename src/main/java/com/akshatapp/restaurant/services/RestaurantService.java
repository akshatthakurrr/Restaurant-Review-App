package com.akshatapp.restaurant.services;

import com.akshatapp.restaurant.domain.RestaurantCreateUpdate;
import com.akshatapp.restaurant.domain.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateUpdate request);
    Page<Restaurant> searchRestaurants(
            String query,
            Float minRating,
            Float latitude,
            Float longitude,
            Float radius,
            Pageable pageable
    );
    Optional<Restaurant> getRestaurant(String id);

    Restaurant updateRestaurant(String id, RestaurantCreateUpdate restaurant);

void deleteRestaurant(String id);
}
