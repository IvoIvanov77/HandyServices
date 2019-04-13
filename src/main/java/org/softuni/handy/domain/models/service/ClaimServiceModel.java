package org.softuni.handy.domain.models.service;

import org.hibernate.validator.constraints.Length;
import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.models.validation_constants.ValidationConstraints;
import org.softuni.handy.domain.models.validation_constants.ValidationMessages;

import javax.validation.constraints.NotNull;

public class ClaimServiceModel extends BaseServiceModel {
    private String reason;

    private ServiceOrder serviceOrder;

    private ProfessionalService professionalService;

    private boolean isClosed;

    @NotNull(message = ValidationMessages.REASON_NOT_NULL_ERROR_MESSAGE)
    @Length(min = ValidationConstraints.REASON_MIN_LENGTH,
            max = ValidationConstraints.REASON_MAX_LENGTH,
            message = ValidationMessages.INVALID_REASON_ERROR_MESSAGE)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @NotNull(message = ValidationMessages.ORDER_NOT_NULL_ERROR_MESSAGE)
    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @NotNull(message = ValidationMessages.PROFESSIONAL_SERVICE_NOT_NULL_ERROR_MESSAGE)
    public ProfessionalService getProfessionalService() {
        return professionalService;
    }

    public void setProfessionalService(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
