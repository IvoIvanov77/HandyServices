package org.softuni.handy.services;

import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ProfessionalServiceModel;
import org.softuni.handy.repositories.ProfessionalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;


@Service
public class ProfessionalServiceServiceImpl implements ProfessionalServiceService {

    private final ProfessionalServiceRepository professionalServiceRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public ProfessionalServiceServiceImpl(ProfessionalServiceRepository professionalServiceRepository,
                                          ModelMapper modelMapper, Validator validator) {
        this.professionalServiceRepository = professionalServiceRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }


    @Override
    public boolean registerService(ProfessionalServiceModel serviceModel){
        if(this.validator.validate(serviceModel).size() > 0){
            return false;
        }
        ProfessionalService professionalService = this.modelMapper
                .map(serviceModel, ProfessionalService.class);
        professionalService.setServiceStatus(ServiceStatus.PENDING);

        String debug = "";
        try {
            this.professionalServiceRepository.saveAndFlush(professionalService);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
