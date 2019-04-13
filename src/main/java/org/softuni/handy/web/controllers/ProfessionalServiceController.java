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
import org.softuni.handy.web.anotations.PageTitle;
import org.softuni.handy.web.web_constants.PageTitles;
import org.softuni.handy.web.web_constants.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/service")
public class ProfessionalServiceController extends BaseController {

    private final ProfessionalServiceService professionalServiceService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfessionalServiceController(ProfessionalServiceService professionalServiceService,
                                         ModelMapper modelMapper) {
        this.professionalServiceService = professionalServiceService;
        this.modelMapper = modelMapper;

    }

    @GetMapping("/create")
    @PageTitle(PageTitles.CREATE_SERVICE_FORM)
    public ModelAndView createServiceView(
            @ModelAttribute("model")ProfessionalServiceBindingModel bindingModel){
        return this.view(Templates.CREATE_SERVICE_FORM);
    }

    @PostMapping("/create")
    public ModelAndView createServiceAction(Authentication authentication,
                                            @Valid @ModelAttribute("model")ProfessionalServiceBindingModel bindingModel,
                                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return this.view(Templates.CREATE_SERVICE_FORM);
        }
        ProfessionalServiceModel serviceModel
                = this.modelMapper.map(bindingModel, ProfessionalServiceModel.class);
        LocationServiceModel location
                = this.locationService.getOneById(bindingModel.getLocationId());
        ServiceTypeServiceModel serviceType
                = this.serviceTypeService.getOneById(bindingModel.getServiceTypeId());
        serviceModel.setLocation(location);
        serviceModel.setServiceType(serviceType);
        serviceModel.setUser(this.currentUser(authentication));
        if(this.professionalServiceService.registerService(serviceModel)){
            return this.redirect("/");
        }

        return this.view(Templates.CREATE_SERVICE_FORM);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle(PageTitles.ADMIN_SERVICES_TABLE)
    @GetMapping("/admin/{status}")
    public ModelAndView adminServicesView(@PathVariable("status") String status){
        List<ProfessionalServiceTableViewModel> tableViewServices
                = this.professionalServiceService.getAllByStatus(status)
                .stream()
                .map(ProfessionalServiceTableViewModel::new)
                .collect(Collectors.toList());
        return this.view(Templates.ADMIN_PANEL_LAYOUT, Templates.SERVICES_TABLE)
                .addObject("services", tableViewServices);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/details/{id}")
    @PageTitle(PageTitles.ADMIN_SERVICE_DETAILS)
    public ModelAndView adminServiceDetailsView(@PathVariable("id") String id){
        ProfessionalServiceModel serviceModel = this.professionalServiceService.getOneByID(id);
        if(serviceModel == null){
            throw new IllegalArgumentException("not existing service");
        }
        ProfessionalServiceDetailsAdminViewModel viewModel =
                new ProfessionalServiceDetailsAdminViewModel(serviceModel);

        return this.view(Templates.ADMIN_PANEL_LAYOUT, Templates.ADMIN_SERVICE_DETAILS)
                .addObject("viewModel", viewModel);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/edit-status/{status}")
    public ModelAndView editStatusAction(@PathVariable String status, HttpServletRequest req){
        String id = req.getParameter("id");
        ProfessionalServiceModel serviceModel = this.professionalServiceService.getOneByID(id);
        serviceModel.setServiceStatus(ServiceStatus.valueOf(status.toUpperCase()));
        return redirect("/service/details/" + id);
    }

}
