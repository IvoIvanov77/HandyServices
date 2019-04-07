package org.softuni.handy.web.controllers;

import org.softuni.handy.domain.models.binding.LocationBindingModel;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.domain.models.view.LocationViewModel;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Validator;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/location")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class LocationController extends BaseController {

    private static final String CREATE_LOCATION_PAGE = "fragments/forms/create-location-form";
    private final DtoMapper mapper;
    private final Validator validator;

    @Autowired
    public LocationController(DtoMapper mapper, Validator validator) {
        this.mapper = mapper;
        this.validator = validator;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createLocationView(
            @ModelAttribute("model") LocationBindingModel bindingModel){
        return this.view(CREATE_LOCATION_PAGE);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createLocationAction(@ModelAttribute("model")LocationBindingModel bindingModel) throws IOException, ExecutionException, InterruptedException {
        LocationServiceModel serviceModel
                = this.mapper.map(bindingModel, LocationServiceModel.class);
        String imageUrl = this.cloudinaryService.uploadImage(bindingModel.getImageUrl()).get();
        serviceModel.setLocationPicture(imageUrl);

        if(this.locationService.addLocation(serviceModel)){
            return this.redirect("/");
        }
        return this.view(CREATE_LOCATION_PAGE);
    }

    @GetMapping("/fetch-all")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<LocationViewModel> fetchLocations() {
        return this.mapper.map(this.locationService.getOrderedLocations(), LocationViewModel.class)
                .collect(Collectors.toList());
    }
}
