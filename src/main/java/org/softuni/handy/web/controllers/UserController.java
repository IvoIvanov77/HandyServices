package org.softuni.handy.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.models.binding.UserRegisterBindingModel;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.softuni.handy.services.UserService;
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

    private static final String GUEST_FORMS_LAYOUT = "guest-forms";
    private static final String REGISTRATION_FORM = "fragments/forms/registration-form";
    private static final String LOGIN_FORM = "fragments/forms/login-form";


    private final ModelMapper mapper;
    private final UserService userService;

    @Autowired
    public UserController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView  register(@ModelAttribute("model") UserRegisterBindingModel model){
        return this.view(GUEST_FORMS_LAYOUT, REGISTRATION_FORM);
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("model") UserRegisterBindingModel bindingModel,
                                 BindingResult bindingResult) {
        if(!bindingModel.getConfirmPassword().equals(bindingModel.getPassword())){
            return this.view(GUEST_FORMS_LAYOUT, REGISTRATION_FORM);
        }
        if(bindingResult.hasErrors()) {
            return this.view(GUEST_FORMS_LAYOUT, REGISTRATION_FORM);
        }

        UserServiceModel serviceModel = this.mapper.map(bindingModel, UserServiceModel.class);
        if(this.userService.registerUser(serviceModel)){
            return this.redirect("/user/login");
        }

        return this.view(GUEST_FORMS_LAYOUT, REGISTRATION_FORM);

    }

    @GetMapping("/login")    public ModelAndView login(){
        return this.view(GUEST_FORMS_LAYOUT, LOGIN_FORM);
    }




}
