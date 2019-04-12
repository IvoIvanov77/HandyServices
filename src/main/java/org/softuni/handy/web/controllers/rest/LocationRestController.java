package org.softuni.handy.web.controllers.rest;

import org.softuni.handy.domain.models.view.LocationViewModel;
import org.softuni.handy.util.DtoMapper;
import org.softuni.handy.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/location")
@RestController
public class LocationRestController extends BaseController {

    private final DtoMapper mapper;

    @Autowired
    public LocationRestController(DtoMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/fetch-all")
    @PreAuthorize("isAuthenticated()")
    public List<LocationViewModel> fetchLocations() {
        return this.mapper.map(this.locationService.getOrderedLocations(), LocationViewModel.class)
                .collect(Collectors.toList());
    }
}
