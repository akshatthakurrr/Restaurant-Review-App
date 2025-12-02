package com.akshatapp.restaurant.domain.dtos;

import com.akshatapp.restaurant.domain.entities.Address;
import com.akshatapp.restaurant.domain.entities.OperatingHours;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantCreateUpdateDto {
    @NotBlank(message="cuisine type is required")
    private String name; // Restaurant's name
    @NotBlank(message ="required")
    private String cuisineType; // Type of cuisine served
    @NotBlank(message = "required")
    private String contactInformation; // Contact details
      @Valid
    private AddressDto address; // Physical location
   @Valid
    private OperatingHoursDto operatingHours; // Opening hours
    @Size(min=1,message = "atleast one photoid is required")
    private List<String> photoIds; // References to uploaded photos
}
