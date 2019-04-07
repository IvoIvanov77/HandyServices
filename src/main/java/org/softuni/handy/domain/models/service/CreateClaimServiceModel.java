package org.softuni.handy.domain.models.service;

public class CreateClaimServiceModel extends BaseServiceModel {
    private String reason;

    private String serviceOrder;

    private String professionalService;

    private boolean isClosed;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(String serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public String getProfessionalService() {
        return professionalService;
    }

    public void setProfessionalService(String professionalService) {
        this.professionalService = professionalService;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
