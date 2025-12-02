package com.akshatapp.restaurant.domain.dtos;


import com.akshatapp.restaurant.domain.entities.Address;
import com.akshatapp.restaurant.domain.entities.OperatingHours;
import com.akshatapp.restaurant.domain.entities.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {
    private String id;

    private String name;
    private String cuisineType;

    private String contactInformation;

    private Float averageRating;
    private GeoPointDto geoLocation;

    private AddressDto address;

    private OperatingHoursDto operatingHours;

    private List<PhotoDto> photos = new ArrayList<>();
    private List<ReviewDto> reviews = new ArrayList<>();

    private UserDto createdBy;
    private Integer totalReviews;
}