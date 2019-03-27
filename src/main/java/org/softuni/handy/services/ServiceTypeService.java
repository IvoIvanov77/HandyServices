package org.softuni.handy.services;

import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;

import java.util.List;

public interface ServiceTypeService {
    List<ServiceTypeServiceModel> getOrderedServiceTypes();

    boolean addServiceType(ServiceTypeServiceModel serviceModel);

    ServiceTypeServiceModel getOneById(String id);
}
