package org.softuni.handy.services;

import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.enums.Role;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ProfessionalServiceModel;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.softuni.handy.exception.InvalidServiceModelException;
import org.softuni.handy.exception.ResourceNotFoundException;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.softuni.handy.services.constants.ErrorMessages;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProfessionalServiceServiceImpl extends BaseService implements ProfessionalServiceService {

    private final ProfessionalServiceRepository professionalServiceRepository;
    private final DtoMapper mapper;
    private final UserService userService;

    @Autowired
    public ProfessionalServiceServiceImpl(ProfessionalServiceRepository professionalServiceRepository,
                                          DtoMapper mapper, Validator validator, UserService userService) {
        super(validator);
        this.professionalServiceRepository = professionalServiceRepository;
        this.mapper = mapper;
        this.userService = userService;
    }


    @Override
    public boolean registerService(ProfessionalServiceModel serviceModel){
        if(hasErrors(serviceModel)){
            throw new InvalidServiceModelException(ErrorMessages.INVALID_PROFESSIONAL_SERVICE_MODEL_ERROR_MESSAGE);
        }        
        ProfessionalService professionalService = this.mapper
                .map(serviceModel, ProfessionalService.class);

        if(this.professionalServiceRepository.
                getFirstByUserUsernameAndLocationAndServiceType(
                        professionalService.getUser().getUsername(), 
                        professionalService.getLocation(), 
                        professionalService.getServiceType()
                ).isPresent()){
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
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PROFESSIONAL_SERVICE_NOT_FOUND_ERROR_MESSAGE));
        return this.mapper.map(professionalService, ProfessionalServiceModel.class);
    }

    @Override
    public boolean editService(ProfessionalServiceModel serviceModel){
        if(hasErrors(serviceModel)){
            throw new InvalidServiceModelException(ErrorMessages.INVALID_PROFESSIONAL_SERVICE_MODEL_ERROR_MESSAGE);
        }
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
        // has other approved professional_services - do nothing
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
