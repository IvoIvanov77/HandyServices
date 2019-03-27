package org.softuni.handy.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.domain.models.view.LocationListViewModel;
import org.softuni.handy.domain.models.view.ServiceTypeListViewModel;
import org.softuni.handy.repositories.UserRoleRepository;
import org.softuni.handy.services.LocationService;
import org.softuni.handy.services.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseController {

    private static final String BASE_LAYOUT = "base-layout";

    @Autowired
    protected   LocationService locationService;

    @Autowired
    protected ServiceTypeService serviceTypeService;

    @Autowired
    protected UserRoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    protected BaseController(){

    }

    protected ModelAndView view(String baseView, String fragment){
        ModelAndView modelAndView = new ModelAndView(baseView);
        modelAndView.addObject("view", fragment);
        return modelAndView;
    }

    protected ModelAndView view(String fragment){
        return this.view(BASE_LAYOUT, fragment);
    }

    protected ModelAndView redirect(String url){
        return new ModelAndView("redirect:" + url);
    }

    @ModelAttribute("locations")
    public List<LocationListViewModel> locations() {
        return locationService.getOrderedLocations()
                .stream()
                .map(serviceModel -> this.modelMapper.map(serviceModel, LocationListViewModel.class))
                .collect(Collectors.toList());
    }

    @ModelAttribute("serviceTypes")
    public List<ServiceTypeListViewModel> serviceTypes() {
        return serviceTypeService.getOrderedServiceTypes()
                .stream()
                .map(serviceModel -> this.modelMapper
                        .map(serviceModel, ServiceTypeListViewModel.class))
                .collect(Collectors.toList());
    }

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        return authentication== null ? null : (User)authentication.getPrincipal();
    }

    @ModelAttribute("userRoles")
    public List<UserRole> userRoles() {
        return this.roleRepository.findAll();
    }
}
