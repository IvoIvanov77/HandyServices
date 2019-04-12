package org.softuni.handy.services;

import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.enums.Role;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ProfessionalServiceModel;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProfessionalServiceServiceImpl implements ProfessionalServiceService {

    private final ProfessionalServiceRepository professionalServiceRepository;
    private final DtoMapper mapper;
    private final Validator validator;
    private final UserService userService;

    @Autowired
    public ProfessionalServiceServiceImpl(ProfessionalServiceRepository professionalServiceRepository,
                                          DtoMapper mapper, Validator validator, UserService userService) {
        this.professionalServiceRepository = professionalServiceRepository;
        this.mapper = mapper;
        this.validator = validator;
        this.userService = userService;
    }


    @Override
    public boolean registerService(ProfessionalServiceModel serviceModel){
        if(this.validator.validate(serviceModel).size() > 0){
            return false;
        }        
        ProfessionalService professionalService = this.mapper
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
        List<ProfessionalService> professionalServices =
                this.professionalServiceRepository.
                        getAllByServiceStatus(ServiceStatus.valueOf(status.toUpperCase()));
       return this.mapToServiceModel(professionalServices);
    }

    @Override
    public List<ProfessionalServiceModel> getAllByUsername(String username){
        List<ProfessionalService> professionalServices =
                this.professionalServiceRepository.getAllByUserUsername(username);
        return this.mapToServiceModel(professionalServices);
    }

    @Override
    public ProfessionalServiceModel getOneByID(String id){
        ProfessionalService professionalService = this.professionalServiceRepository.findById(id)
                .orElse(null);
        return professionalService == null ? null :
                this.mapper.map(professionalService, ProfessionalServiceModel.class);
    }

    @Override
    public boolean editService(ProfessionalServiceModel serviceModel){
        ProfessionalService professionalService = this.mapper
                .map(serviceModel, ProfessionalService.class);
        try {
            this.professionalServiceRepository.save(professionalService);
            this.updateUserRoles(professionalService);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<ProfessionalServiceModel> searchByLocationAndType(List<String> locations,
                                                 List<String> types){
        List<ProfessionalService> professionalServices =
                this.professionalServiceRepository.getAllByLocationAndServiceType(locations,types);
        return this.mapToServiceModel(professionalServices);
    }

    private void updateUserRoles(ProfessionalService professionalService){
        User user = professionalService.getUser();
        boolean hasApprovedServices =
                this.professionalServiceRepository.
                        getAllByServiceStatusAndUserUsername(ServiceStatus.APPROVED, user.getUsername())
                        .size() > 0;
        //if owner is admin or rejected service owner
        // has other approved services - do nothing
        if(user.getAuthorities().size() > 2 ||
                (professionalService.getServiceStatus().equals(ServiceStatus.REJECTED) && hasApprovedServices)){
            return;
        }
        if(professionalService.getServiceStatus().equals(ServiceStatus.APPROVED)){
            user.setAuthorities(this.userService.setUserRoles(Role.ROLE_SERVICE_MAN));
        }else {
            user.setAuthorities(this.userService.setUserRoles(Role.ROLE_USER));
        }
        this.userService.editUser(this.mapper.map(user, UserServiceModel.class));
    }

    private List<ProfessionalServiceModel> mapToServiceModel(List<ProfessionalService> services){
        return this.mapper.map(services, ProfessionalServiceModel.class).collect(Collectors.toList());
    }


}
