package org.softuni.handy.services;

import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.exception.InvalidServiceModelException;
import org.softuni.handy.exception.ResourceNotFoundException;
import org.softuni.handy.repositories.LocationRepository;
import org.softuni.handy.services.constants.ErrorMessages;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl extends BaseService implements LocationService {

    private final LocationRepository locationRepository;

    private final DtoMapper mapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, DtoMapper mapper, Validator validator) {
        super(validator);
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
        if(hasErrors(serviceModel)){
            throw new InvalidServiceModelException(ErrorMessages.INVALID_LOCATION_MODEL);
        }
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
       Location location = this.locationRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.LOCATION_NOT_FOUND));
        return this.mapper.map(location, LocationServiceModel.class);
    }

}
