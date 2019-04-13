package org.softuni.handy.web.controllers;

import org.softuni.handy.domain.models.binding.ClaimBindingModel;
import org.softuni.handy.domain.models.service.CreateClaimServiceModel;
import org.softuni.handy.domain.models.view.ClaimListViewModel;
import org.softuni.handy.services.ClaimService;
import org.softuni.handy.util.DtoMapper;
import org.softuni.handy.web.anotations.PageTitle;
import org.softuni.handy.web.web_constants.PageTitles;
import org.softuni.handy.web.web_constants.Templates;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public ModelAndView createClaim(@Valid @ModelAttribute ClaimBindingModel bindingModel,
                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return redirect("/order/client/completed");
        }
        this.claimService.openClaim(this.mapper.map(bindingModel, CreateClaimServiceModel.class));
        return redirect("/order/client/claimed");
    }


    @GetMapping("/client/open-claims")
    @PageTitle(PageTitles.CLIENT_CLAIMS)
    public ModelAndView clientOpenedClaims(Authentication authentication){
        List<ClaimListViewModel> clientClaims = this
                .mapper.map(this.claimService
                        .getUserClaims(authentication.getName(), false, false),
                        ClaimListViewModel.class)
                .collect(Collectors.toList());
        return view(Templates.CLIENT_CLAIMS)
                .addObject("claims", clientClaims);
    }

    @PostMapping("/close")
    public ModelAndView closeClaim(HttpServletRequest request){
        this.claimService.closeClaim(request.getParameter("claimId"));
        return redirect("/order/client/completed");
    }
}
