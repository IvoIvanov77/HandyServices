package org.softuni.handy.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.models.binding.LocationBindingModel;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Validator;

@Controller
@RequestMapping("/location")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class LocationController extends BaseController {

    private static final String CREATE_LOCATION_PAGE = "fragments/forms/create-location-form";
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public LocationController(ModelMapper modelMapper, Validator validator) {
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createLocationView(
            @ModelAttribute("model") LocationBindingModel bindingModel){
        return this.view(CREATE_LOCATION_PAGE);
    }

    @PostMapping("/create")
    public ModelAndView createLocationAction(@ModelAttribute("model")LocationBindingModel bindingModel) {
        LocationServiceModel serviceModel
                = this.modelMapper.map(bindingModel, LocationServiceModel.class);

        if(this.locationService.addLocation(serviceModel)){
            return this.redirect("/");
        }
        return this.view(CREATE_LOCATION_PAGE);
    }
}
