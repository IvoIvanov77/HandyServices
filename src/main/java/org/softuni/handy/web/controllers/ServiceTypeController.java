package org.softuni.handy.web.controllers;

import org.softuni.handy.domain.models.binding.ServiceTypeBindingModel;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.domain.models.view.ServiceTypeByLocationViewModel;
import org.softuni.handy.domain.models.view.ServiceTypeViewModel;
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
@RequestMapping("/category")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ServiceTypeController extends BaseController {

    private static final String CREATE_CATEGORY_PAGE = "fragments/forms/create-service-type-form";
    private final DtoMapper mapper;
    private final Validator validator;

    @Autowired
    public ServiceTypeController(DtoMapper mapper, Validator validator) {
        this.mapper = mapper;
        this.validator = validator;
    }

    @GetMapping("/create")
    public ModelAndView createCategoryView(
            @ModelAttribute("model") ServiceTypeBindingModel bindingModel){
        return this.view(CREATE_CATEGORY_PAGE);
    }

    @PostMapping("/create")
    public ModelAndView createCategoryAction(@ModelAttribute("model")ServiceTypeBindingModel bindingModel) throws IOException, ExecutionException, InterruptedException {
        ServiceTypeServiceModel serviceModel
                = this.mapper.map(bindingModel, ServiceTypeServiceModel.class);
        String imageUrl = this.cloudinaryService.uploadImage(bindingModel.getImage()).get();
        serviceModel.setServicePicture(imageUrl);

        if(this.serviceTypeService.addServiceType(serviceModel)){
            return this.redirect("/");
        }
        return this.view(CREATE_CATEGORY_PAGE);
    }

    @GetMapping("/location/{locationId}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getServiceTypesByLocation(@PathVariable String locationId) {
        List<ServiceTypeByLocationViewModel> viewModels = this.mapper.map(
                this.serviceTypeService.getServiceTypesByApprovedServicesAndByLocation(locationId),
                ServiceTypeByLocationViewModel.class
        ).collect(Collectors.toList());
        viewModels.forEach(viewModel -> viewModel.setLocationId(locationId));
        return this.view("user-categories")
                .addObject("viewModels", viewModels);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getCategories() {
        List<ServiceTypeViewModel> categoriesList = this.mapper
                .map(this.serviceTypeService.getOrderedServiceTypes(), ServiceTypeViewModel.class)
                .collect(Collectors.toList());
        return view("admin/admin-panel-layout","admin/all-categories")
                .addObject("categories", categoriesList);
    }
}
