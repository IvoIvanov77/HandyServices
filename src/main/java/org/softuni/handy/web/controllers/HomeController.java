package org.softuni.handy.web.controllers;

import org.softuni.handy.web.anotations.PageTitle;
import org.softuni.handy.web.web_constants.PageTitles;
import org.softuni.handy.web.web_constants.Templates;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {


    @GetMapping("/")
    public ModelAndView homeView(Authentication authentication) {

        if (authentication == null) {
            return new ModelAndView(Templates.LANDING_PAGE);
        }
        return this.view(Templates.USER_HOME_PAGE);

    }

    @PageTitle(PageTitles.USER_HOME_PAGE)
    @GetMapping("/home")
    public ModelAndView home() {
        return this.view(Templates.USER_HOME_PAGE);
    }


}
