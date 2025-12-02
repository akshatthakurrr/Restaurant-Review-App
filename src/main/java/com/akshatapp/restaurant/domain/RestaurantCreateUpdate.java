package com.akshatapp.restaurant.domain;

import com.akshatapp.restaurant.domain.entities.Address;
import com.akshatapp.restaurant.domain.entities.OperatingHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RestaurantCreateUpdate {
    private String name; // Restaurant's name
    private String cuisineType; // Type of cuisine served
    private String contactInformation; // Contact details
    private Address address; // Physical location
    private OperatingHours operatingHours; // Opening hours
    private List<String> photoIds; // References to uploaded photos
}
