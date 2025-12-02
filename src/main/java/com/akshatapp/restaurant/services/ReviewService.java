package com.akshatapp.restaurant.services;

import com.akshatapp.restaurant.domain.ReviewCreateUpdate;
import com.akshatapp.restaurant.domain.entities.Review;
import com.akshatapp.restaurant.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ReviewService {
    Review createReview(User author, String restaurantId, ReviewCreateUpdate review);
    Page<Review>listReview(String restaurantId, Pageable pageable);
    Optional<Review> getReview(String restaurantId, String reviewId);

    Review updateReview(User author, String restaurantId, String reviewId, ReviewCreateUpdate review);
//    Review updateReview(User author, String restaurantId, String reviewId, ReviewCreateUpdate review);

}
