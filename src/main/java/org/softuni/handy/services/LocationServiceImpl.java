package org.softuni.handy.services;

import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.repositories.LocationRepository;
import org.softuni.handy.util.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final DtoMapper mapper;

    public LocationServiceImpl(LocationRepository locationRepository, DtoMapper mapper) {
        this.locationRepository = locationRepository;
        this.mapper = mapper;
    }


    @Override
    public List<LocationServiceModel> getOrderedLocations(){
        return this.mapper.map(this.locationRepository.findAll(), LocationServiceModel.class)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public boolean addLocation(LocationServiceModel serviceModel){
        try{
            this.locationRepository.updatePriorities(serviceModel.getPriority());
            this.locationRepository
                    .saveAndFlush(this.mapper.map(serviceModel, Location.class));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public LocationServiceModel getOneById(String id){
       Location location = this.locationRepository.getOne(id);
        return this.mapper.map(location, LocationServiceModel.class);
    }



}
