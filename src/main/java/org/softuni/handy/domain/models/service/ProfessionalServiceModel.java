package org.softuni.handy.domain.models.service;


import org.hibernate.validator.constraints.Length;
import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.validation_constants.ValidationConstraints;
import org.softuni.handy.domain.models.validation_constants.ValidationMessages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ProfessionalServiceModel extends BaseServiceModel {

    private User user;

    private String firstName;

    private String lastName;

    private ServiceTypeServiceModel serviceType;

    private LocationServiceModel location;

    private String phoneNumber;

    private String slogan;

    private String serviceDescription;

    private ServiceStatus serviceStatus;

    private byte rating;

    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Pattern(regexp = ValidationConstraints.PERSON_NAME_PATTERN,
            message = ValidationMessages.INVALID_FIRST_NAME_ERROR_MESSAGE)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Pattern(regexp = ValidationConstraints.PERSON_NAME_PATTERN,
            message = ValidationMessages.INVALID_LAST_NAME_ERROR_MESSAGE)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull(message = ValidationMessages.INVALID_SERVICE_TYPE_ERROR_MESSAGE)
    public ServiceTypeServiceModel getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeServiceModel serviceType) {
        this.serviceType = serviceType;
    }

    @NotNull(message = ValidationMessages.INVALID_SERVICE_LOCATION_ERROR_MESSAGE)
    public LocationServiceModel getLocation() {
        return location;
    }

    public void setLocation(LocationServiceModel location) {
        this.location = location;
    }

    @Pattern(regexp = ValidationConstraints.PHONE_NUMBER_PATTERN,
            message = ValidationMessages.INVALID_PHONE_NUMBER_ERROR_MESSAGE)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Length(min = ValidationConstraints.MIN_SLOGAN_LENGTH, max = ValidationConstraints.MAX_SLOGAN_LENGTH,
            message = ValidationMessages.SLOGAN_LENGTH_ERROR_MESSAGE)
    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    @Length(min = ValidationConstraints.MIN_SERVICE_DESCRIPTION_LENGTH,
            max = ValidationConstraints.MAX_SERVICE_DESCRIPTION_LENGTH,
            message = ValidationMessages.DESCRIPTION_LENGTH_ERROR_MESSAGE)
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


    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }
}
