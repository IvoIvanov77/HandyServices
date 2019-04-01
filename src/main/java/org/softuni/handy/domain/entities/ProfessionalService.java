package org.softuni.handy.domain.entities;

import org.softuni.handy.domain.enums.ServiceStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "professional_services")
public class ProfessionalService extends CustomerAction {

    private String slogan;

    private String serviceDescription;

    private ServiceStatus serviceStatus;


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


}
