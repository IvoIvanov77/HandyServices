package org.softuni.handy.web.controllers;

import org.softuni.handy.domain.models.binding.ClaimBindingModel;
import org.softuni.handy.domain.models.service.CreateClaimServiceModel;
import org.softuni.handy.services.ClaimService;
import org.softuni.handy.util.DtoMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/claim")
public class ClaimController extends BaseController {

    private final ClaimService claimService;

    private final DtoMapper mapper;


    public ClaimController(ClaimService claimService, DtoMapper mapper) {
        this.claimService = claimService;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public ModelAndView createClaim(@ModelAttribute ClaimBindingModel bindingModel){
        this.claimService.openClaim(this.mapper.map(bindingModel, CreateClaimServiceModel.class));
        return redirect("/order/client/completed");
    }
}
