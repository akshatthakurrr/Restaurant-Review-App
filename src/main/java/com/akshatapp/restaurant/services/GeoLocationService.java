package com.akshatapp.restaurant.services;


import com.akshatapp.restaurant.domain.GeoLocation;
import com.akshatapp.restaurant.domain.entities.Address;

public interface GeoLocationService {
    GeoLocation geoLocate(Address address);

}
