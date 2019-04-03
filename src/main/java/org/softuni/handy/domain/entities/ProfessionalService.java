package org.softuni.handy.domain.entities;

import org.softuni.handy.domain.enums.ServiceStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "professional_services")
public class ProfessionalService extends CustomerAction {

    private String slogan;

    private String serviceDescription;

    private ServiceStatus serviceStatus;

    private List<ServiceOrder> orders;

    private List<ServiceOffer> offers;

    @Column(name = "slogan", nullable = false)
    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    @Column(name = "service_description", nullable = false,  columnDefinition="text")
    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    @Enumerated
    @Column(name = "service_status", nullable = false)
    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    @OneToMany(mappedBy = "professionalService")
    public List<ServiceOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ServiceOrder> orders) {
        this.orders = orders;
    }

    @OneToMany(mappedBy = "professionalService")
    public List<ServiceOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<ServiceOffer> offers) {
        this.offers = offers;
    }
}