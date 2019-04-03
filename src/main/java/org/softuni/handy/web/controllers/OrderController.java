package org.softuni.handy.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.binding.OrderBindingModel;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.domain.models.service.ServiceOrderServiceModel;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.domain.models.view.OrderDetailsViewModel;
import org.softuni.handy.domain.models.view.OrderListViewModel;
import org.softuni.handy.services.OrderServiceImpl;
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

    private final OrderServiceImpl orderService;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public OrderController(OrderServiceImpl orderService, ModelMapper modelMapper,
                           Validator validator) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }


    @GetMapping("/create")
    public ModelAndView createService(@ModelAttribute("model")OrderBindingModel bindingModel,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location){
        return this.view(CREATE_ORDER_FORM)
                .addObject("selectedLocation", location)
                .addObject("selectedType", category);
    }

    @PostMapping("/create")
    public ModelAndView createServiceAction(Authentication authentication,
                                            @ModelAttribute("model") OrderBindingModel bindingModel){
        ServiceOrderServiceModel serviceModel
                = this.modelMapper.map(bindingModel, ServiceOrderServiceModel.class);
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
    @GetMapping("/my-opportunities")
    public ModelAndView ordersByCurrentServiceMan(){
        return this.view("my-pending-orders");
    }

    @GetMapping("/fetch-my-orders")
    @PreAuthorize("hasRole('ROLE_SERVICE_MAN')")
    @ResponseBody
    public List<OrderListViewModel> fetchCurrentServiceManPendingOrders(Authentication authentication) {
        return this.orderService
                .getOrdersByUserRegisteredServices(authentication.getName(), OrderStatus.PENDING)
                .stream()
                .map(serviceModel -> this.modelMapper.map(serviceModel, OrderListViewModel.class))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_SERVICE_MAN')")
    @GetMapping("/details/{id}")
    public ModelAndView orderDetailsView(@PathVariable String id){
        ServiceOrderServiceModel serviceModel = this.orderService.getById(id);
        OrderDetailsViewModel viewModel = this.modelMapper
                .map(serviceModel, OrderDetailsViewModel.class);
        return this.view("order-details")
                .addObject("viewModel", viewModel);
    }

    @GetMapping("/client/{status}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView fetchCurrentUserOrders(Authentication authentication,
                                                           @PathVariable String status) {
        List<OrderListViewModel> orders = this.orderService
                .getOrdersByUserAndStatus(authentication.getName(),
                        OrderStatus.valueOf(status.toUpperCase()))
                .stream()
                .map(serviceModel -> this.modelMapper.map(serviceModel, OrderListViewModel.class))
                .collect(Collectors.toList());
        return this.view("/my-orders")
                .addObject("orders", orders);
    }


}
