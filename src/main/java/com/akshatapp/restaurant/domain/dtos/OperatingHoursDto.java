package com.akshatapp.restaurant.domain.dtos;

import com.akshatapp.restaurant.domain.entities.TimeRange;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OperatingHoursDto {
    @Valid
    private TimeRangeDto monday; // TimeRange for Monday
    @Valid
        private TimeRangeDto tuesday; // TimeRange for Tuesday
    @Valid
        private TimeRangeDto wednesday; // TimeRange for Wednesday

     @Valid   private TimeRangeDto thursday; // TimeRange for Thursday

    @Valid
        private TimeRangeDto friday; // TimeRange for Friday
     @Valid
    private TimeRangeDto saturday; // TimeRange for Saturday
    @Valid
        private TimeRangeDto sunday; // TimeRange for Sunday
}
