package org.softuni.handy.domain.models.service;

import org.hibernate.validator.constraints.Length;
import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.validation_constants.ValidationConstraints;
import org.softuni.handy.domain.models.validation_constants.ValidationMessages;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class ServiceOrderServiceModel extends BaseServiceModel {

    private User user;

    private String firstName;

    private String lastName;

    private ServiceTypeServiceModel serviceType;

    private LocationServiceModel location;

    private String phoneNumber;

    private String address;

    private String problemDescription;

    private ProfessionalServiceModel professionalService;

    private OrderStatus orderStatus;

    private LocalDate scheduledDate;

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

    @NotNull
    public ServiceTypeServiceModel getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeServiceModel serviceType) {
        this.serviceType = serviceType;
    }

    @NotNull
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

    @Length(min = ValidationConstraints.MIN_ADDRESS_LENGTH, max = ValidationConstraints.MAX_ADDRESS_LENGTH,
            message = ValidationMessages.ADDRESS_LENGTH_ERROR_MESSAGE)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(min = ValidationConstraints.MIN_ORDER_DESCRIPTION_LENGTH,
            max = ValidationConstraints.MAX_ORDER_DESCRIPTION_LENGTH,
            message = ValidationMessages.ORDER_DESCRIPTION_LENGTH_ERROR_MESSAGE)
    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public ProfessionalServiceModel getProfessionalService() {
        return professionalService;
    }

    public void setProfessionalService(ProfessionalServiceModel professionalService) {
        this.professionalService = professionalService;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @NotNull(message = ValidationMessages.NOT_NULL_SCHEDULED_DATE_ERROR_MESSAGE)
    @Future(message = ValidationMessages.INVALID_DATE_ERROR_MESSAGE)
    @DateTimeFormat(pattern = ValidationConstraints.LOCAL_DATE_FORMAT_PATTERN)
    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}
