package org.softuni.handy.web.controllers;

import org.softuni.handy.web.anotations.PageTitle;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    private static final String LANDING_PAGE = "landing-page";
    private static final String USER_HOME_PAGE = "home-user";


    @GetMapping("/")
    @PageTitle("Home")
    public ModelAndView homeView(Authentication authentication) {

        if (authentication == null) {
            return new ModelAndView(LANDING_PAGE);
        }
        return this.view(USER_HOME_PAGE);

    }

    @PageTitle("Home")
    @GetMapping("/home")
    public ModelAndView home() {
        return this.view(USER_HOME_PAGE);
    }


}
