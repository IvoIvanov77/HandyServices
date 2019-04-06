package org.softuni.handy.domain.models.service;

public class CreateClaimServiceModel extends BaseServiceModel {
    private String reason;

    private String serviceOrderId;

    private String professionalServiceId;

    private boolean isClosed;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public String getProfessionalServiceId() {
        return professionalServiceId;
    }

    public void setProfessionalServiceId(String professionalServiceId) {
        this.professionalServiceId = professionalServiceId;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
