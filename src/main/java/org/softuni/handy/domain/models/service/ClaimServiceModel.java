package org.softuni.handy.domain.models.service;

import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.ServiceOrder;

public class ClaimServiceModel extends BaseServiceModel {
    private String reason;

    private ServiceOrder serviceOrder;

    private ProfessionalService professionalService;

    private boolean isClosed;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

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
