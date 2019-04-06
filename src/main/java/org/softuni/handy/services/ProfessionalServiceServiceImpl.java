package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.enums.Role;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ProfessionalServiceModel;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProfessionalServiceServiceImpl implements ProfessionalServiceService {

    private final ProfessionalServiceRepository professionalServiceRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;
    private final UserService userService;

    @Autowired
    public ProfessionalServiceServiceImpl(ProfessionalServiceRepository professionalServiceRepository,
                                          ModelMapper modelMapper, Validator validator, UserService userService) {
        this.professionalServiceRepository = professionalServiceRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.userService = userService;
    }


    @Override
    public boolean registerService(ProfessionalServiceModel serviceModel){
        if(this.validator.validate(serviceModel).size() > 0){
            return false;
        }        
        ProfessionalService professionalService = this.modelMapper
                .map(serviceModel, ProfessionalService.class);

        if(this.professionalServiceRepository.
                getFirstByUserUsernameAndLocationAndServiceType(
                        professionalService.getUser().getUsername(), 
                        professionalService.getLocation(), 
                        professionalService.getServiceType()
                ).isPresent()){
            //// TODO: 4/5/2019  add exception
            return false;
        }
        
        professionalService.setServiceStatus(ServiceStatus.PENDING);

        try {
            this.professionalServiceRepository.saveAndFlush(professionalService);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<ProfessionalServiceModel> getAllByStatus(String status){
       return this.professionalServiceRepository.getAllByServiceStatus(ServiceStatus.valueOf(status.toUpperCase()))
                .stream()
                .map(professionalService ->
                        this.modelMapper.map(professionalService, ProfessionalServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfessionalServiceModel> getAllByUsername(String username){
        return this.professionalServiceRepository.getAllByUserUsername(username)
                .stream()
                .map(professionalService ->
                        this.modelMapper.map(professionalService, ProfessionalServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessionalServiceModel getOneByID(String id){
        ProfessionalService professionalService = this.professionalServiceRepository.findById(id)
                .orElse(null);
        return professionalService == null ? null :
                this.modelMapper.map(professionalService, ProfessionalServiceModel.class);
    }

    @Override
    public boolean editService(ProfessionalServiceModel serviceModel){
        ProfessionalService professionalService = this.modelMapper
                .map(serviceModel, ProfessionalService.class);
        try {
            this.updateUserRoles(professionalService);
            this.professionalServiceRepository.saveAndFlush(professionalService);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<ProfessionalServiceModel> searchByLocationAndType(List<String> locations,
                                                 List<String> types){
        return this.professionalServiceRepository
                .getAllByLocationAndServiceType(locations,types)
                .stream()
                .map(ps -> this.modelMapper.map(ps, ProfessionalServiceModel.class))
                .collect(Collectors.toList());
    }

    private void updateUserRoles(ProfessionalService professionalService){
        User user = professionalService.getUser();
        if(user.getAuthorities().size() > 2){
            return;
        }
        if(professionalService.getServiceStatus().equals(ServiceStatus.APPROVED)){
            user.setAuthorities(this.userService.setUserRoles(Role.ROLE_SERVICE_MAN));
        }else {
            user.setAuthorities(this.userService.setUserRoles(Role.ROLE_USER));
        }
        this.userService.editUser(this.modelMapper.map(user, UserServiceModel.class));
    }


}
