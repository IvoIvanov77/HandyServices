package org.softuni.handy.services;

import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.domain.enums.Role;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.softuni.handy.exception.InvalidServiceModelException;
import org.softuni.handy.repositories.UserRepository;
import org.softuni.handy.repositories.UserRoleRepository;
import org.softuni.handy.services.constants.ErrorMessages;
import org.softuni.handy.util.DtoMapper;
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
public class UserServiceImpl extends BaseService implements UserService {

    private final UserRepository userRepository;

    private final DtoMapper mapper;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, DtoMapper mapper,
                           Validator validator, BCryptPasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository) {
        super(validator);
        this.userRepository = userRepository;
        this.mapper = mapper;
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
        return this.userRepository.getUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND_ERROR_MESSAGE));
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
        if(hasErrors(serviceModel)){
            throw new InvalidServiceModelException(ErrorMessages.INVALID_USER_MODEL_ERROR_MESSAGE);
        }
        User user = this.mapper.map(serviceModel, User.class);
        String password = this.passwordEncoder.encode(serviceModel.getPassword());
        user.setPassword(password);
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
        return this.mapper.map(this.userRepository.findAll(), UserServiceModel.class)
                .sorted((u1, u2) -> u2.getAuthorities().size() - u1.getAuthorities().size())
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
