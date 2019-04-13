package org.softuni.handy.web.controllers;

import org.softuni.handy.domain.models.binding.ServiceTypeBindingModel;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.domain.models.view.ServiceTypeByLocationViewModel;
import org.softuni.handy.domain.models.view.ServiceTypeViewModel;
import org.softuni.handy.util.DtoMapper;
import org.softuni.handy.web.anotations.PageTitle;
import org.softuni.handy.web.web_constants.PageTitles;
import org.softuni.handy.web.web_constants.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ServiceTypeController extends BaseController {

    private final DtoMapper mapper;

    @Autowired
    public ServiceTypeController(DtoMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle(PageTitles.CREATE_CATEGORY_PAGE)
    public ModelAndView createCategoryView(
            @ModelAttribute("model") ServiceTypeBindingModel bindingModel){
        return this.view(Templates.CREATE_CATEGORY_PAGE);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createCategoryAction(
            @Valid @ModelAttribute("model")ServiceTypeBindingModel bindingModel,
            BindingResult bindingResult) throws IOException, ExecutionException, InterruptedException {
        if(bindingResult.hasErrors()) {
            return this.view(Templates.CREATE_CATEGORY_PAGE);
        }
        ServiceTypeServiceModel serviceModel
                = this.mapper.map(bindingModel, ServiceTypeServiceModel.class);
        String imageUrl = this.cloudinaryService.uploadImage(bindingModel.getImage()).get();
        serviceModel.setServicePicture(imageUrl);

        if(this.serviceTypeService.addServiceType(serviceModel)){
            return this.redirect("/");
        }
        return this.view(Templates.CREATE_CATEGORY_PAGE);
    }

    @GetMapping("/location/{locationId}")
    @PageTitle(PageTitles.CLIENT_CATEGORIES)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getServiceTypesByLocation(@PathVariable String locationId) {
        List<ServiceTypeByLocationViewModel> viewModels = this.mapper.map(
                this.serviceTypeService.getServiceTypesByApprovedServicesAndByLocation(locationId),
                ServiceTypeByLocationViewModel.class
        ).collect(Collectors.toList());
        viewModels.forEach(viewModel -> viewModel.setLocationId(locationId));
        return this.view(Templates.CLIENT_CATEGORIES)
                .addObject("viewModels", viewModels);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle(PageTitles.ADMIN_ALL_CATEGORIES)
    public ModelAndView getCategories() {
        List<ServiceTypeViewModel> categoriesList = this.mapper
                .map(this.serviceTypeService.getOrderedServiceTypes(), ServiceTypeViewModel.class)
                .collect(Collectors.toList());
        return view(Templates.ADMIN_PANEL_LAYOUT, Templates.ADMIN_ALL_CATEGORIES)
                .addObject("categories", categoriesList);
    }
}
