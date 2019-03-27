package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository,
                               ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;

    }


    @Override
    public List<LocationServiceModel> getOrderedLocations(){
        return this.locationRepository.findAll()
                .stream()
                .sorted()
                .map(location -> this.modelMapper.map(location, LocationServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addLocation(LocationServiceModel serviceModel){
        try{
            this.locationRepository.updatePriorities(serviceModel.getPriority());
            this.locationRepository
                    .saveAndFlush(this.modelMapper.map(serviceModel, Location.class));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public LocationServiceModel getOneById(String id){
       Location location = this.locationRepository.getOne(id);
       LocationServiceModel serviceModel
               = this.modelMapper.map(location, LocationServiceModel.class);
       return serviceModel;
    }



}
