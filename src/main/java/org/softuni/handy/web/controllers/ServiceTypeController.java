package org.softuni.handy.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.models.binding.ServiceTypeBindingModel;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
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
@RequestMapping("/category")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ServiceTypeController extends BaseController {

    private static final String CREATE_CATEGORY_PAGE = "fragments/forms/create-service-type-form";
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public ServiceTypeController(ModelMapper modelMapper, Validator validator) {
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @GetMapping("/create")
    public ModelAndView createCategoryView(
            @ModelAttribute("model") ServiceTypeBindingModel bindingModel){
        return this.view(CREATE_CATEGORY_PAGE);
    }

    @PostMapping("/create")
    public ModelAndView createCategoryAction(@ModelAttribute("model")ServiceTypeBindingModel bindingModel) {
        ServiceTypeServiceModel serviceModel
                = this.modelMapper.map(bindingModel, ServiceTypeServiceModel.class);

        if(this.serviceTypeService.addServiceType(serviceModel)){
            return this.redirect("/");
        }
        return this.view(CREATE_CATEGORY_PAGE);
    }
}
