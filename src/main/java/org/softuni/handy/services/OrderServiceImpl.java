package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.service.ServiceOrderServiceModel;
import org.softuni.handy.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import javax.validation.Validator;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper,
                            Validator validator) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
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


}
