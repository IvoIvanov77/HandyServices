package org.softuni.handy.domain.models.binding;

import org.hibernate.validator.constraints.Length;
import org.softuni.handy.domain.models.validation_constants.ValidationConstraints;
import org.softuni.handy.domain.models.validation_constants.ValidationMessages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ProfessionalServiceBindingModel {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String slogan;

    private String serviceDescription;

    private String serviceTypeId;

    private String locationId;

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

    @NotNull(message = ValidationMessages.INVALID_SERVICE_TYPE_ERROR_MESSAGE)
    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @NotNull(message = ValidationMessages.INVALID_SERVICE_LOCATION_ERROR_MESSAGE)
    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}

