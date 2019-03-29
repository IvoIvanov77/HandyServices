package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.domain.enums.Role;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.softuni.handy.repositories.UserRepository;
import org.softuni.handy.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final Validator validator;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           Validator validator, BCryptPasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }


    private Set<UserRole> getRolesForRegistration() {

        if (this.userRepository.count() == 0) {
            return this.setUserRoles(Role.ROLE_ROOT_ADMIN);
        }

       return this.setUserRoles(Role.ROLE_USER);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails result = this.userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return result;
    }

    //add all roles with lower authority
    @Override
    public Set<UserRole> setUserRoles(Role role){
        Set<UserRole> roles = new HashSet<>();
        for (Role r : Role.values()) {
            UserRole roleToAdd = this.userRoleRepository.getUserRoleByAuthority(r.name());
            roles.add(roleToAdd);
            if(r.equals(role)){
                break;
            }
        }
        return roles;
    }


    @Override
    public boolean registerUser(UserServiceModel serviceModel){
        User user = this.modelMapper.map(serviceModel, User.class);
        user.setPassword(this.passwordEncoder.encode(serviceModel.getPassword()));
        user.setAuthorities(this.getRolesForRegistration());
        try{
            this.userRepository.saveAndFlush(user);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<UserServiceModel> allUsers(){
        return this.userRepository.findAll()
                .stream()
                .sorted((u1, u2) -> u2.getAuthorities().size() - u1.getAuthorities().size())
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public boolean editUser(UserServiceModel serviceModel)  {
        User user = (User) this.loadUserByUsername(serviceModel.getUsername());
        user.setAuthorities(serviceModel.getAuthorities());
        user.setAccountNonLocked(serviceModel.isAccountNonLocked());
        try{
            this.userRepository.saveAndFlush(user);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
