package com.akshatapp.restaurant.mappers;

import com.akshatapp.restaurant.domain.ReviewCreateUpdate;
import com.akshatapp.restaurant.domain.dtos.ReviewCreateUpdateDto;
import com.akshatapp.restaurant.domain.dtos.ReviewDto;
import com.akshatapp.restaurant.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
    ReviewCreateUpdate toReviewCreateUpdate(ReviewCreateUpdateDto dto);

    ReviewDto toDto(Review review);
}
