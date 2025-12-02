package com.akshatapp.restaurant.controllers;

import com.akshatapp.restaurant.domain.ReviewCreateUpdate;
import com.akshatapp.restaurant.domain.dtos.ReviewCreateUpdateDto;
import com.akshatapp.restaurant.domain.dtos.ReviewDto;
import com.akshatapp.restaurant.domain.entities.Review;
import com.akshatapp.restaurant.domain.entities.User;
import com.akshatapp.restaurant.mappers.ReviewMapper;
import com.akshatapp.restaurant.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants/{restaurantId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable String restaurantId,
            @Valid @RequestBody ReviewCreateUpdateDto review,
            @AuthenticationPrincipal Jwt jwt) {
// Convert the review DTO to a domain object

        ReviewCreateUpdate reviewCreateUpdate
                = reviewMapper.toReviewCreateUpdate(review);
// Extract user details from JWT
        User user = jwtToUser(jwt);
// Create the review
        Review createdReview = reviewService.createReview(
                user, restaurantId,reviewCreateUpdate);
// Return the created review as DTO

        return ResponseEntity.ok(reviewMapper.toDto(createdReview));
    }
    @GetMapping
    public Page<ReviewDto>listReiews(
            @PathVariable String restaurantId,
            @PageableDefault(size =20,
                    page= 0,
                    sort="datePosted"
                    , direction = Sort.Direction.DESC)Pageable pageable

            ){
                return  reviewService.listReview(restaurantId,pageable)
                .map(reviewMapper::toDto);


    }
    @GetMapping(path="/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId
    ){
        return reviewService.getReview(restaurantId, reviewId)
                .map(reviewMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.noContent().build());
    }

    private User jwtToUser(Jwt jwt) {
        return new User(
                jwt.getSubject(), // User's unique ID
                jwt.getClaimAsString("preferred_username"), // Username
                jwt.getClaimAsString("given_name"), // First name
                jwt.getClaimAsString("family_name") // Last name
        );
    }
}