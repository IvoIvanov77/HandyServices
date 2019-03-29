package org.softuni.handy.domain.models.view;

import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ProfessionalServiceModel;

public class ProfessionalServiceDetailsAdminViewModel {

    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String serviceType;

    private String location;

    private String phoneNumber;

    private String slogan;

    private String serviceDescription;

    private ServiceStatus serviceStatus;

    public ProfessionalServiceDetailsAdminViewModel() {
    }

    public ProfessionalServiceDetailsAdminViewModel(ProfessionalServiceModel serviceModel) {
        this.id = serviceModel.getId();
        this.username = serviceModel.getUser().getUsername();
        this.firstName = serviceModel.getFirstName();
        this.lastName = serviceModel.getLastName();
        this.phoneNumber = serviceModel.getPhoneNumber();
        this.serviceType = serviceModel.getServiceType().getServiceName();
        this.location = serviceModel.getLocation().getTown();
        this.serviceStatus = serviceModel.getServiceStatus();
        this.slogan = serviceModel.getSlogan();
        this.serviceDescription = serviceModel.getServiceDescription();
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
