package org.softuni.handy.web.controllers;

import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.binding.OrderBindingModel;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.domain.models.service.ServiceOrderServiceModel;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.domain.models.view.OrderDetailsViewModel;
import org.softuni.handy.domain.models.view.OrderListViewModel;
import org.softuni.handy.services.OrderService;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private static final String CREATE_ORDER_FORM = "fragments/forms/create-order-form";

    private final OrderService orderService;

    private final Validator validator;
    private final DtoMapper mapper;

    @Autowired
    public OrderController(OrderService orderService, Validator validator, DtoMapper mapper) {
        this.orderService = orderService;
        this.validator = validator;
        this.mapper = mapper;
    }

    @GetMapping("/create")
    public ModelAndView createOrder(@ModelAttribute("model")OrderBindingModel bindingModel,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location){
        return this.view(CREATE_ORDER_FORM)
                .addObject("selectedLocation", location)
                .addObject("selectedType", category);
    }

    //// TODO: 4/7/2019 move logic in controller
    @PostMapping("/create")
    public ModelAndView createOrderAction(Authentication authentication,
                                            @ModelAttribute("model") OrderBindingModel bindingModel){
        ServiceOrderServiceModel serviceModel
                = this.mapper.map(bindingModel, ServiceOrderServiceModel.class);
        LocationServiceModel location
                = this.locationService.getOneById(bindingModel.getLocationId());
        ServiceTypeServiceModel serviceType
                = this.serviceTypeService.getOneById(bindingModel.getServiceTypeId());
        serviceModel.setLocation(location);
        serviceModel.setServiceType(serviceType);
        serviceModel.setUser(this.currentUser(authentication));
        this.orderService.createOrder(serviceModel);
        return this.redirect("/");
    }

    @PreAuthorize("hasRole('ROLE_SERVICE_MAN')")
    @GetMapping("/pro/pending-orders")
    public ModelAndView pendingOrdersByCurrentServiceMan(Authentication authentication){
        return this.getProPendingOrders(authentication, false);
    }

    @PreAuthorize("hasRole('ROLE_SERVICE_MAN')")
    @GetMapping("/pro/offered-orders")
    public ModelAndView offeredOrdersByCurrentServiceMan(Authentication authentication){
        return this.getProPendingOrders(authentication, true);
    }

    @PreAuthorize("hasRole('ROLE_SERVICE_MAN')")
    @GetMapping("/pro/accepted-orders")
    public ModelAndView acceptedOrdersByCurrentServiceMan(Authentication authentication){
        return this.getOrdersByCurrentServiceManAndStatus(authentication, OrderStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_SERVICE_MAN')")
    @GetMapping("/pro/completed-orders")
    public ModelAndView completedOrdersByCurrentServiceMan(Authentication authentication){
        return this.getOrdersByCurrentServiceManAndStatus(authentication, OrderStatus.COMPLETED);
    }

    @PreAuthorize("hasRole('ROLE_SERVICE_MAN')")
    @GetMapping("/details/{id}")
    public ModelAndView orderDetailsView(@PathVariable String id){
        ServiceOrderServiceModel serviceModel = this.orderService.getById(id);
        OrderDetailsViewModel viewModel = this.mapper.map(serviceModel, OrderDetailsViewModel.class);
        return this.view("order-details").addObject("viewModel", viewModel);
    }

    @GetMapping("/client/{status}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView fetchCurrentUserOrders(Authentication authentication, @PathVariable String status) {
        List<ServiceOrderServiceModel> orderServiceModels = this.orderService
                .getOrdersByUserAndStatus(authentication.getName(),OrderStatus.valueOf(status.toUpperCase()));

        List<OrderListViewModel> orders = this.mapper.map(orderServiceModels, OrderListViewModel.class)
                .collect(Collectors.toList());
        return this.view("/my-orders")
                .addObject("orders", orders);
    }



    @PostMapping("/complete/{id}")
    @PreAuthorize("hasRole('ROLE_SERVICE_MAN')")
    public ModelAndView completeOrder(@PathVariable String id){
        if(this.orderService.updateOrderStatus(id, OrderStatus.COMPLETED)){
            return redirect("/order/pro/accepted-orders");
        }
        return redirect("/order/details/" + id);
    }

    private List<OrderListViewModel> getPendingOrdersByOffersCondition(boolean hasOffer, String username ){
        List<ServiceOrderServiceModel> orderServiceModels =
                this.orderService.getOrdersByUserRegisteredServices(username, hasOffer, OrderStatus.PENDING);
        return this.mapper.map(orderServiceModels, OrderListViewModel.class).collect(Collectors.toList());

    }

    private ModelAndView getProPendingOrders(Authentication authentication, boolean hasOffer){
        return this.view("my-pending-orders")
                .addObject("orders",
                        this.getPendingOrdersByOffersCondition(hasOffer, authentication.getName()));

    }

    private ModelAndView getOrdersByCurrentServiceManAndStatus(Authentication authentication, OrderStatus status) {
        List<ServiceOrderServiceModel> serviceModels = this.orderService
                .getOrdersByStatusAndServiceUserName(authentication.getName(), status);
        List<OrderListViewModel> orders = this.mapper.map(serviceModels, OrderListViewModel.class)
                .collect(Collectors.toList());
        return this.view("my-pending-orders")
                .addObject("orders", orders);
    }


}
