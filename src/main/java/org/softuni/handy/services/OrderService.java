package org.softuni.handy.services;

import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.service.ServiceOrderServiceModel;

import java.util.List;

public interface OrderService {


    List<ServiceOrderServiceModel> getOrdersByUserRegisteredServices(String username,
                                                                     boolean offersContainsUser,
                                                                     OrderStatus... statuses);

    ServiceOrderServiceModel getById(String id);

    List<ServiceOrderServiceModel> getOrdersByUserAndStatus(String username, OrderStatus status);

    List<ServiceOrderServiceModel> getOrdersByStatusAndServiceUserName(String username,
                                                                       OrderStatus status);

    boolean updateOrder(ServiceOrderServiceModel serviceModel);

    boolean updateOrderStatus(String orderId, OrderStatus orderStatus);
}
