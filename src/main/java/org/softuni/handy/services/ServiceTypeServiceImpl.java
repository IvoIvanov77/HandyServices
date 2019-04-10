package org.softuni.handy.services;

import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.repositories.ServiceTypeRepository;
import org.softuni.handy.util.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    private final DtoMapper mapper;

    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository,
                                  DtoMapper mapper) {
        this.serviceTypeRepository = serviceTypeRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ServiceTypeServiceModel> getOrderedServiceTypes() {
        return this.mapper.map(this.serviceTypeRepository.findAll(), ServiceTypeServiceModel.class)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public boolean addServiceType(ServiceTypeServiceModel serviceModel) {
        try {
            this.serviceTypeRepository
                    .updatePriorities(serviceModel.getPriority());
            this.serviceTypeRepository
                    .save(this.mapper.map(serviceModel, ServiceType.class));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ServiceTypeServiceModel getOneById(String id) {
        ServiceType serviceType = this.serviceTypeRepository.getOne(id);
        return this.mapper.map(serviceType, ServiceTypeServiceModel.class);
    }

    @Override
    public List<ServiceTypeServiceModel> getServiceTypesByApprovedServicesAndByLocation(String id) {
        List<ServiceType> resultList =
                this.serviceTypeRepository.serviceTypesByLocation(id, ServiceStatus.APPROVED);
        return this.mapper.map(resultList, ServiceTypeServiceModel.class)
                .sorted()
                .collect(Collectors.toList());

    }
}
