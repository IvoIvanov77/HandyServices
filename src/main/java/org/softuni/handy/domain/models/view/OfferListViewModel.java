package org.softuni.handy.domain.models.view;

import org.softuni.handy.domain.entities.ServiceOrder;

import java.math.BigDecimal;

public class OfferListViewModel {

    private String id;

    private Integer hours;

    private BigDecimal price;

    private ServiceOrder serviceOrder;

    private String professionalServiceFirstName;

    private String professionalServiceLastName;

    private String professionalServicePhoneNumber;

    private String professionalServiceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public String getProfessionalServiceFirstName() {
        return professionalServiceFirstName;
    }

    public void setProfessionalServiceFirstName(String professionalServiceFirstName) {
        this.professionalServiceFirstName = professionalServiceFirstName;
    }

    public String getProfessionalServiceLastName() {
        return professionalServiceLastName;
    }

    public void setProfessionalServiceLastName(String professionalServiceLastName) {
        this.professionalServiceLastName = professionalServiceLastName;
    }

    public String getProfessionalServicePhoneNumber() {
        return professionalServicePhoneNumber;
    }

    public void setProfessionalServicePhoneNumber(String professionalServicePhoneNumber) {
        this.professionalServicePhoneNumber = professionalServicePhoneNumber;
    }

    public String getProfessionalServiceId() {
        return professionalServiceId;
    }

    public void setProfessionalServiceId(String professionalServiceId) {
        this.professionalServiceId = professionalServiceId;
    }
}
