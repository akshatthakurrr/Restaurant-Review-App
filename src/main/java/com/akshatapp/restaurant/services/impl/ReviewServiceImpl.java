package com.akshatapp.restaurant.services.impl;

import com.akshatapp.restaurant.domain.ReviewCreateUpdate;
import com.akshatapp.restaurant.domain.entities.Photo;
import com.akshatapp.restaurant.domain.entities.Restaurant;
import com.akshatapp.restaurant.domain.entities.Review;
import com.akshatapp.restaurant.domain.entities.User;
import com.akshatapp.restaurant.exeptions.RestaurantNotFoundException;
import com.akshatapp.restaurant.exeptions.ReviewNotAllowedException;
import com.akshatapp.restaurant.repositories.RestaurantRepositories;
import com.akshatapp.restaurant.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final RestaurantRepositories restaurantRepositories;

    @Override
    public Review createReview(User author, String restaurantId, ReviewCreateUpdate reviewData) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);


        boolean hasExistingReview = restaurant.getReviews().stream()
                .anyMatch(r -> r.getWrittenBy().getId().equals(author.getId()));

        if (hasExistingReview) {
            throw new ReviewNotAllowedException("User has already reviewed this restaurant");
        }

        LocalDateTime now = LocalDateTime.now();

        // Create photos
        List<Photo> photos = reviewData.getPhotoIds().stream()
                .map(url -> {
                    Photo photo = new Photo();
                    photo.setUrl(url);
                    photo.setUploadDate(now);
                    return photo;
                })
                .collect(Collectors.toList());

        // Create review
        String reviewId = UUID.randomUUID().toString();
        Review ReviewToCreate = Review.builder()
                .id(reviewId)
                .content(reviewData.getContent())
                .rating(reviewData.getRating())
                .photos(photos)
                .datePosted(now)
                .lastEdited(now)
                .writtenBy(author)
                .build();

        // Add review to restaurant
        restaurant.getReviews().add(ReviewToCreate);

        // Update restaurant's average rating
        updateRestaurantAverageRating(restaurant);

        // Save restaurant with new review
        Restaurant savedRestaurant = restaurantRepositories.save(restaurant);

        return getReviewFromRestaurant(reviewId, savedRestaurant)
                .orElseThrow(()-> new RuntimeException("error retrieving creaated review"));

    }

    @Override
    public Page<Review> listReview(String restaurantId, Pageable pageable) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);

        List<Review> reviews = restaurant.getReviews();
        Sort sort = pageable.getSort();
        if(sort.isSorted()){
            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();
            boolean isAscending = order.getDirection().isAscending();
            Comparator<Review>comparator= switch (property){
                case"datePosted"  -> Comparator.comparing(Review::getDatePosted);
                case"rating"-> Comparator.comparing(Review::getRating);
                default -> Comparator.comparing(Review::getDatePosted);

            };
            reviews.sort(isAscending ? comparator: comparator.reversed());
        }else{
            reviews.sort(Comparator.comparing(Review::getDatePosted).reversed());
        }
        int start = (int)pageable.getOffset();
        if(start>=reviews.size()){
            return new PageImpl<>(Collections.emptyList(),pageable, reviews.size());
        }
        int end =Math.min((start + pageable.getPageSize()), reviews.size());
         return  new PageImpl<>(reviews.subList(start, end), pageable, reviews.size());
    }

    @Override
    public Optional<Review> getReview(String restaurantId, String reviewId) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);
        return getReviewFromRestaurant(reviewId, restaurant);
    }

    @Override
    public Review updateReview(User author, String restaurantId, String reviewId, ReviewCreateUpdate review) {
        return null;
    }

    private static Optional<Review> getReviewFromRestaurant(String reviewId, Restaurant restaurant) {
        return restaurant.getReviews().stream().filter(r -> reviewId.equals(r.getId()))
                .findFirst();
    }

//    @Override
//    public Review updateReview(User author, String restaurantId, String reviewId, ReviewCreateUpdate review) {
//        Restaurant restaurant = getRestaurantOrThrow(restaurantId);
//
//        String authorId = author.getId();
//        Review existingReview = getReviewFromRestaurant(reviewId, restaurant)
//                .orElseThrow(() -> new ReviewNotAllowedException("Review does not exist"));
//
//        if(!authorId.equals(existingReview.getWrittenBy().getId())){
//            throw new ReviewNotAllowedException("cannot update another user review");
//        }
//        if(LocalDateTime.now().isAfter(existingReview.getDatePosted().plusHours(48))){
//            throw new ReviewNotAllowedException("Review no longer edited");
//
//        }
//
//        existingReview.setContent(review.getContent());
//        existingReview.setRating(review.getRating());
//
//
//    }

    private Restaurant getRestaurantOrThrow(String restaurantId) {
        return restaurantRepositories.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        "Restaurant with id not found: " + restaurantId)
                );
    }

    private void updateRestaurantAverageRating(Restaurant restaurant) {
        List<Review> reviews = restaurant.getReviews();
        if (reviews.isEmpty()) {
            restaurant.setAverageRating(0.0f);
        } else {
            float averageRating = (float) reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
            restaurant.setAverageRating(averageRating);
        }
    }
}
