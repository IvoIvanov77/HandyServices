package org.softuni.handy.web.controllers;

import org.softuni.handy.domain.models.binding.AcceptOfferBindingModel;
import org.softuni.handy.domain.models.binding.OfferBindingModel;
import org.softuni.handy.domain.models.service.OfferServiceModel;
import org.softuni.handy.domain.models.view.OfferListViewModel;
import org.softuni.handy.services.OfferService;
import org.softuni.handy.util.DtoMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offer")
public class OfferController extends BaseController {

    private final OfferService offerService;

    private final DtoMapper mapper;

    public OfferController(OfferService offerService, DtoMapper mapper) {
        this.offerService = offerService;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public ModelAndView createOffer(@ModelAttribute OfferBindingModel bindingModel,
                                      Authentication authentication){
        OfferServiceModel serviceModel = this.mapper.map(bindingModel, OfferServiceModel.class);
        if(this.offerService.createOffer(serviceModel, authentication.getName())){
            return redirect("/");
        }
        //// TODO: 4/5/2019 exception?
        return this.redirect("/order/my-opportunities");

    }

    @GetMapping("/{orderId}")
    public ModelAndView offersView(@PathVariable String orderId){
        List<OfferServiceModel> offersList = this.offerService.getAllByOrder(orderId);
        List<OfferListViewModel> offersViewList = this.mapper
                .map(offersList, OfferListViewModel.class).collect(Collectors.toList());
        return this.view("offers-list")
                .addObject("offers", offersViewList);
    }

    @PostMapping("/accept")
    public ModelAndView acceptOffer(@ModelAttribute AcceptOfferBindingModel bindingModel){
        if(this.offerService.acceptOffer(bindingModel)){
            return redirect("/");
        }
        return redirect("/offer/" + bindingModel.getServiceOrderId());
    }



}
