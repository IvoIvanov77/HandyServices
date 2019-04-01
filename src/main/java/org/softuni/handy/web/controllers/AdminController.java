package org.softuni.handy.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.domain.enums.Role;
import org.softuni.handy.domain.models.binding.UserEditBindingModel;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.softuni.handy.domain.models.view.UserDetailsViewModel;
import org.softuni.handy.domain.models.view.UserTableViewModel;
import org.softuni.handy.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    public static final String ADMIN_PANEL_LAYOUT = "admin/admin-panel-layout";
    public static final String ADMIN_HOME_PAGE = "admin/admin-home";
    public static final String ALL_USERS_TABLE = "admin/all-users";
    public static final String EDIT_USER_PAGE = "admin/edit-user";

    private final ModelMapper mapper;
    private final UserService userService;

    public AdminController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("")
    public ModelAndView adminPanel(){
        return this.view(ADMIN_PANEL_LAYOUT, ADMIN_HOME_PAGE);
    }

    @GetMapping("/all-users")
    public ModelAndView allUsers(){
        List<UserTableViewModel> allUsers = this.userService.allUsers()
                .stream()
                .map(this::mapToTableViewModel)
                .collect(Collectors.toList());
        return this.view(ADMIN_PANEL_LAYOUT, ALL_USERS_TABLE)
                .addObject("allUsers", allUsers);
    }


    @GetMapping("/edit/user/{username}")
    public ModelAndView editUserView(@AuthenticationPrincipal UserDetails currentUser,
                                     @PathVariable String username,
                                     @ModelAttribute("bindingModel") UserEditBindingModel bindingModel) {
        if(currentUser.getUsername().equals(username)){
            return this.redirect("/admin/all-users");
        }

        UserDetails userDetails = this.userService.loadUserByUsername(username);
        if (userDetails.getAuthorities().contains(this.getRole(Role.ROLE_ROOT_ADMIN))){
            return this.redirect("/admin/all-users");
        }
        UserDetailsViewModel viewModel = this.mapper.map(userDetails, UserDetailsViewModel.class);
        List <UserRole> roles = this.userRoleService.userRoles();
        roles.remove(this.getRole(Role.ROLE_ROOT_ADMIN));
        return view(ADMIN_PANEL_LAYOUT, EDIT_USER_PAGE)
                .addObject("allRoles", roles)
                .addObject("viewModel", viewModel);

    }

    @PostMapping("/edit/user")
    public ModelAndView editUserAction(@AuthenticationPrincipal UserDetails currentUser,
                                       @ModelAttribute("bindingModel") UserEditBindingModel bindingModel) {
        if(currentUser.getUsername().equals(bindingModel.getUsername())){
            return this.redirect("/admin/all-users");
        }

        UserDetails userDetails = this.userService.loadUserByUsername(bindingModel.getUsername());
        if (userDetails.getAuthorities().contains(this.getRole(Role.ROLE_ROOT_ADMIN))){
            return this.redirect("/admin/all-users");
        }
        UserServiceModel serviceModel = this.mapper.map(bindingModel, UserServiceModel.class);
        serviceModel.setAuthorities(this.userService.setUserRoles(Role.valueOf(bindingModel.getAuthority())));

        if(this.userService.editUser(serviceModel)){
            return this.redirect("/admin/all-users");
        }
        return view(ADMIN_PANEL_LAYOUT, EDIT_USER_PAGE);

    }

    private UserTableViewModel mapToTableViewModel(UserServiceModel serviceModel){
        UserTableViewModel tableViewModel =
                this.mapper.map(serviceModel, UserTableViewModel.class);
        UserRole rootAdmin = this.getRole(Role.ROLE_ROOT_ADMIN);
        tableViewModel.setRoodAdmin(serviceModel.getAuthorities().contains(rootAdmin));
        return tableViewModel;
    }

    private UserRole getRole(Role role){
        return this.userRoleService.userRole(role.name());
    }
}
