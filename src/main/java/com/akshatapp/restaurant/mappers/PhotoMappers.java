package com.akshatapp.restaurant.mappers;

import com.akshatapp.restaurant.domain.dtos.PhotoDto;
import com.akshatapp.restaurant.domain.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface PhotoMappers {
    PhotoDto todto(Photo photo);
}
