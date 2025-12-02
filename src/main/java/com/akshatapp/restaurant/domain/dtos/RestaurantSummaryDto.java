package com.akshatapp.restaurant.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class RestaurantSummaryDto {

        private String id;
        private String name;
        private String cuisineType;
        private Float averageRating;
        private Integer totalReviews;
        private AddressDto address;
        private List<PhotoDto> photos;
}
