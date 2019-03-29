package org.softuni.handy.domain.models.view;

import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ProfessionalServiceModel;

public class ProfessionalServiceTableViewModel {

    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String serviceType;

    private String location;

    private ServiceStatus serviceStatus;

    public ProfessionalServiceTableViewModel() {
    }

    public ProfessionalServiceTableViewModel(ProfessionalServiceModel serviceModel) {
        this.id = serviceModel.getId();
        this.username = serviceModel.getUser().getUsername();
        this.firstName = serviceModel.getFirstName();
        this.lastName = serviceModel.getLastName();
        this.serviceType = serviceModel.getServiceType().getServiceName();
        this.location = serviceModel.getLocation().getTown();
        this.serviceStatus = serviceModel.getServiceStatus();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
