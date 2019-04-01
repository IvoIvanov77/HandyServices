package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.service.ServiceOrderServiceModel;
import org.softuni.handy.repositories.LocationRepository;
import org.softuni.handy.repositories.OrderRepository;
import org.softuni.handy.repositories.ServiceTypeRepository;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;
    private final LocationRepository locationRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper,
                            Validator validator, LocationRepository locationRepository,
                            ServiceTypeRepository serviceTypeRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.locationRepository = locationRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public boolean createOrder(ServiceOrderServiceModel serviceModel){
        if(this.validator.validate(serviceModel).size() > 0){
            return false;
        }
        ServiceOrder serviceOrder = this.modelMapper
                .map(serviceModel, ServiceOrder.class);
        serviceOrder.setOrderStatus(OrderStatus.PENDING);

        String debug = "";
        try {
            this.orderRepository.saveAndFlush(serviceOrder);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<ServiceOrderServiceModel> getOrdersByUserRegisteredServices(String username){
        List<Location> locations = this.locationRepository
                .findAllByServiceMan(username);
        List<ServiceType> serviceTypes = this.serviceTypeRepository
                .findAllByServiceMan(username);
        List<ServiceOrder> serviceOrders = this.orderRepository
                .findAllByLocationInAndServiceTypeIn(locations, serviceTypes);
        return serviceOrders
                .stream()
                .map(serviceOrder -> this.modelMapper.map(serviceOrder, ServiceOrderServiceModel.class))
                .collect(Collectors.toList());
    }




}
