package org.softuni.handy.domain.models.binding;

public class OpenClaimBindingModel {

    private String reason;

    private String serviceOrderId;

    private String professionalServiceId;

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
}
