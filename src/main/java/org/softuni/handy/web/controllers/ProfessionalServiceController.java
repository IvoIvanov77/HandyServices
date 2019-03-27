package org.softuni.handy.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.models.binding.ProfessionalServiceBindingModel;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.domain.models.service.ProfessionalServiceModel;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.services.ProfessionalServiceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Validator;

@Controller
@RequestMapping("/service")
public class ProfessionalServiceController extends BaseController {

    public static final String CREATE_SERVICE_FORM = "fragments/forms/create-service-form";

    private final ProfessionalServiceService professionalServiceService;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public ProfessionalServiceController(ProfessionalServiceService professionalServiceService,
                                         ModelMapper modelMapper, Validator validator) {
        this.professionalServiceService = professionalServiceService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @GetMapping("/create")
    public ModelAndView createServiceView(
            @ModelAttribute("model")ProfessionalServiceBindingModel bindingModel){
        return this.view(CREATE_SERVICE_FORM);
    }

    @PostMapping("/create")
    public ModelAndView createServiceAction(Authentication authentication,
            @ModelAttribute("model")ProfessionalServiceBindingModel bindingModel){
        ProfessionalServiceModel serviceModel
                = this.modelMapper.map(bindingModel, ProfessionalServiceModel.class);
        LocationServiceModel location
                = this.locationService.getOneById(bindingModel.getLocationId());
        ServiceTypeServiceModel serviceType
                = this.serviceTypeService.getOneById(bindingModel.getServiceTypeId());
        serviceModel.setLocation(location);
        serviceModel.setServiceType(serviceType);
        serviceModel.setUser(this.currentUser(authentication));
        this.professionalServiceService.registerService(serviceModel);
        return this.redirect("/");
    }


}
