package com.akshatapp.restaurant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreateUpdate {
    private String content; // The text content of the review
    private Integer rating; // Rating from 1-5
    private List<String> photoIds;
}
