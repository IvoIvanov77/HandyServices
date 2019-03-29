package org.softuni.handy.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.binding.ProfessionalServiceBindingModel;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.domain.models.service.ProfessionalServiceModel;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.domain.models.view.ProfessionalServiceDetailsAdminViewModel;
import org.softuni.handy.domain.models.view.ProfessionalServiceTableViewModel;
import org.softuni.handy.services.ProfessionalServiceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/service")
public class ProfessionalServiceController extends BaseController {

    public static final String CREATE_SERVICE_FORM = "fragments/forms/create-service-form";
    public static final String SERVICES_TABLE = "admin/services-table";
    public static final String ADMIN_PANEL_LAYOUT = "admin/admin-panel-layout";
    public static final String ADMIN_SERVICE_DETAILS = "admin/admin-service-details";

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list/{status}")
    public ModelAndView adminServicesView(@PathVariable("status") String status){
        List<ProfessionalServiceTableViewModel> tableViewServices
                = this.professionalServiceService.getAllByStatus(status)
                .stream()
                .map(ProfessionalServiceTableViewModel::new)
                .collect(Collectors.toList());
        return this.view(ADMIN_PANEL_LAYOUT, SERVICES_TABLE)
                .addObject("services", tableViewServices);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/details/{id}")
    public ModelAndView adminServiceDetailsView(@PathVariable("id") String id){
        ProfessionalServiceModel serviceModel = this.professionalServiceService.getOneByID(id);
        if(serviceModel == null){
            //handle exception
            return null;
        }
        ProfessionalServiceDetailsAdminViewModel viewModel =
                new ProfessionalServiceDetailsAdminViewModel(serviceModel);

        return this.view(ADMIN_PANEL_LAYOUT, ADMIN_SERVICE_DETAILS)
                .addObject("viewModel", viewModel);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/edit-status/{status}")
    public ModelAndView editStatusAction(@PathVariable String status, HttpServletRequest req){
        String id = req.getParameter("id");
        ProfessionalServiceModel serviceModel = this.professionalServiceService.getOneByID(id);
        serviceModel.setServiceStatus(ServiceStatus.valueOf(status.toUpperCase()));
        if(!this.professionalServiceService.editService(serviceModel)){
            //error handling
        }
        return redirect("/service/details/" + id);
    }

    @GetMapping(value = "/search", produces = "application/json")
    @ResponseBody
    public List<ProfessionalServiceModel> search(
            @RequestParam(value="location", required=false) List<String> locations,
            @RequestParam(value="serviceType", required=false) List<String> types){
        return null;
    }

}
