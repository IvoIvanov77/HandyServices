package org.softuni.handy.services;

import org.softuni.handy.domain.models.service.BaseServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public abstract class BaseService {

    private final Validator validator;


    @Autowired
    public BaseService(Validator validator) {
        this.validator = validator;
    }

    protected boolean hasErrors(BaseServiceModel serviceModel){
        Set<ConstraintViolation<BaseServiceModel>> result
                = this.validator.validate(serviceModel);

        return this.validator.validate(serviceModel).size()>0;
    }
}
