package org.softuni.handy.services;

import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.exception.InvalidServiceModelException;
import org.softuni.handy.exception.ResourceNotFoundException;
import org.softuni.handy.repositories.ServiceTypeRepository;
import org.softuni.handy.services.constants.ErrorMessages;
import org.softuni.handy.util.DtoMapper;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceTypeServiceImpl extends BaseService implements ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    private final DtoMapper mapper;

    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository,
                                  DtoMapper mapper, Validator validator) {
        super(validator);
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
        if(hasErrors(serviceModel)){
            throw new InvalidServiceModelException(ErrorMessages.INVALID_SERVICE_TYPE_MODEL);
        }
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
        ServiceType serviceType = this.serviceTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SERVICE_TYPE_NOT_FOUND));
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
