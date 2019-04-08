package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.service.ServiceOrderServiceModel;
import org.softuni.handy.repositories.LocationRepository;
import org.softuni.handy.repositories.OrderRepository;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.softuni.handy.repositories.ServiceTypeRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@CacheConfig(cacheNames={"orders"})
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final LocationRepository locationRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ProfessionalServiceRepository serviceRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper,
                            Validator validator, LocationRepository locationRepository,
                            ServiceTypeRepository serviceTypeRepository,
                            ProfessionalServiceRepository serviceRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.locationRepository = locationRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceRepository = serviceRepository;
    }

    @CachePut
    @Override
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


    @Cacheable
    @Override
    public List<ServiceOrderServiceModel> getOrdersByUserRegisteredServices(String username,
                                                                            boolean offersContainsUser,
                                                                            OrderStatus... statuses){
        List<OrderStatus> statusCollection = statuses.length == 0 ?
                Arrays.asList(OrderStatus.values()) :
                Arrays.asList(statuses);
        List<Location> locations = this.locationRepository
                .findAllByServiceMan(username);
        List<ServiceType> serviceTypes = this.serviceTypeRepository
                .findAllByServiceMan(username);
        List<ServiceOrder> serviceOrders = this.orderRepository
                .findAllByLocationInAndServiceTypeInAndOrderStatusInOrderByScheduledDate(
                        locations, serviceTypes, statusCollection);
        return getOrdersByOffersCondition(serviceOrders, username, offersContainsUser);

    }

    @Override
    public ServiceOrderServiceModel getById(String id){
        ServiceOrder serviceOrder = this.orderRepository.findById(id)
                .orElse(null);
        return serviceOrder == null ? null :
                this.modelMapper.map(serviceOrder, ServiceOrderServiceModel.class);
    }

    @Cacheable
    @Override
    public List<ServiceOrderServiceModel> getOrdersByUserAndStatus(String username, OrderStatus status){
        return this.orderRepository.findAllByUserUsernameAndOrderStatus(username, status)
                .stream()
                .map(serviceOrder -> this.modelMapper
                        .map(serviceOrder,ServiceOrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Cacheable
    @Override
    public List<ServiceOrderServiceModel> getOrdersByStatusAndServiceUserName(String username,
                                                                              OrderStatus status){
        Collection<ProfessionalService> professionalServices =
                this.serviceRepository.getAllByUserUsername(username);
        return this.orderRepository.findAllByOrderStatusAndProfessionalServiceIn(status, professionalServices)
                .stream()
                .map(serviceOrder -> this.modelMapper
                        .map(serviceOrder,ServiceOrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @CachePut
    @Override
    public boolean updateOrder(ServiceOrderServiceModel serviceModel){
        ServiceOrder serviceOrder = this.modelMapper.map(serviceModel, ServiceOrder.class);
        try{
            this.orderRepository.save(serviceOrder);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @CachePut
    @Override
    public boolean updateOrderStatus(String orderId, OrderStatus orderStatus){
        ServiceOrderServiceModel serviceModel = this.getById(orderId);
        serviceModel.setOrderStatus(orderStatus);
        return this.updateOrder(serviceModel);
    }


    private boolean isOrderHasOfferByUser(ServiceOrder serviceOrder, String username){
        return serviceOrder.getOffers()
                .stream()
                .anyMatch(serviceOffer -> serviceOffer
                        .getProfessionalService()
                        .getUser()
                        .getUsername()
                        .equals(username));
    }

    private List<ServiceOrderServiceModel> getOrdersByOffersCondition(List<ServiceOrder> orders,
                                                                      String username,
                                                                      boolean offersContainsUser){
        return orders.stream()
                .filter(serviceOrder -> offersContainsUser ==
                        this.isOrderHasOfferByUser(serviceOrder, username))
                .map(serviceOrder -> this.modelMapper.map(serviceOrder,
                        ServiceOrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 1 0 * * ?")
    void deactivateOrder() {
        List<ServiceOrder> expiredOrders = this.orderRepository
                .findAllByOrderStatusAndScheduledDateBefore(OrderStatus.PENDING, LocalDate.now());

        expiredOrders.forEach(serviceOrder -> serviceOrder.setOrderStatus(OrderStatus.EXPIRED));

        this.orderRepository.saveAll(expiredOrders);
    }

}
