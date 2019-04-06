package org.softuni.handy.domain.models.binding;

public class AcceptOfferBindingModel {

    private String offerId;

    private String serviceOrderId;

    private String professionalServiceId;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
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
