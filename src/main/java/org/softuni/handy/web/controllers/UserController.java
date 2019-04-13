package org.softuni.handy.web.controllers;

import org.softuni.handy.domain.models.binding.UserRegisterBindingModel;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.softuni.handy.services.UserService;
import org.softuni.handy.util.DtoMapper;
import org.softuni.handy.web.anotations.PageTitle;
import org.softuni.handy.web.web_constants.PageTitles;
import org.softuni.handy.web.web_constants.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    private final DtoMapper mapper;
    private final UserService userService;

    @Autowired
    public UserController(DtoMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("/register")
    @PageTitle(PageTitles.REGISTRATION_FORM)
    public ModelAndView  register(@ModelAttribute("model") UserRegisterBindingModel model){
        return this.view(Templates.GUEST_FORMS_LAYOUT, Templates.REGISTRATION_FORM);
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("model") UserRegisterBindingModel bindingModel,
                                 BindingResult bindingResult) {
        if(!bindingModel.getConfirmPassword().equals(bindingModel.getPassword())){
            return this.view(Templates.GUEST_FORMS_LAYOUT, Templates.REGISTRATION_FORM);
        }
        if(bindingResult.hasErrors()) {
            return this.view(Templates.GUEST_FORMS_LAYOUT, Templates.REGISTRATION_FORM);
        }

        UserServiceModel serviceModel = this.mapper.map(bindingModel, UserServiceModel.class);
        if(this.userService.registerUser(serviceModel)){
            return this.redirect("/user/login");
        }
        return this.view(Templates.GUEST_FORMS_LAYOUT, Templates.REGISTRATION_FORM);

    }

    @GetMapping("/login")
    @PageTitle(PageTitles.LOGIN_FORM)
    public ModelAndView login(){
        return this.view(Templates.GUEST_FORMS_LAYOUT, Templates.LOGIN_FORM);
    }

}
