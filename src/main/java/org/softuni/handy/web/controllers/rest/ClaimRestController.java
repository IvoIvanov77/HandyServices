package org.softuni.handy.web.controllers.rest;

import org.softuni.handy.domain.models.view.ClaimRestViewModel;
import org.softuni.handy.services.ClaimService;
import org.softuni.handy.util.DtoMapper;
import org.softuni.handy.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/claim")
@RestController
public class ClaimRestController extends BaseController {

    private final DtoMapper mapper;

    private final ClaimService claimService;

    @Autowired
    public ClaimRestController(DtoMapper mapper, ClaimService claimService) {
        this.mapper = mapper;
        this.claimService = claimService;
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public ClaimRestViewModel fetchClaimByOrder(@PathVariable String orderId) {
        return this.mapper
                .map(this.claimService.getOpenedOrderClaim(orderId), ClaimRestViewModel.class);
    }
}
