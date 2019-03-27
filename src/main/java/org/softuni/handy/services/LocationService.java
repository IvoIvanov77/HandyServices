package org.softuni.handy.services;

import org.softuni.handy.domain.models.service.LocationServiceModel;

import java.util.List;

public interface LocationService {
    List<LocationServiceModel> getOrderedLocations();

    boolean addLocation(LocationServiceModel serviceModel);

    LocationServiceModel getOneById(String id);
}
