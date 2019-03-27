package org.softuni.handy.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.models.binding.OrderBindingModel;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.domain.models.service.ServiceOrderServiceModel;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.services.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Validator;

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


}
