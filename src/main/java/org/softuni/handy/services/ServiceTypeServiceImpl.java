package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.repositories.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService{

    private final ServiceTypeRepository serviceTypeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository,
                                  ModelMapper modelMapper) {
        this.serviceTypeRepository = serviceTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ServiceTypeServiceModel> getOrderedServiceTypes(){
        return this.serviceTypeRepository.findAll()
                .stream()
                .sorted()
                .map(serviceType ->  this.modelMapper
                        .map(serviceType, ServiceTypeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addServiceType(ServiceTypeServiceModel serviceModel){
        try{
            this.serviceTypeRepository
                    .updatePriorities(serviceModel.getPriority());
            this.serviceTypeRepository
                    .saveAndFlush(this.modelMapper.map(serviceModel, ServiceType.class));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ServiceTypeServiceModel getOneById(String id){
        ServiceType serviceType = this.serviceTypeRepository.getOne(id);
        ServiceTypeServiceModel serviceModel
                = this.modelMapper.map(serviceType, ServiceTypeServiceModel.class);
        return serviceModel;
    }
}
