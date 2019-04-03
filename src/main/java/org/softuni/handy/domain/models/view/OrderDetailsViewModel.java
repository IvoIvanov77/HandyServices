package org.softuni.handy.domain.models.view;

import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;

import java.time.LocalDate;

public class OrderDetailsViewModel {

    private String id;

    private String firstName;

    private String lastName;

    private ServiceTypeServiceModel serviceType;

    private LocationServiceModel location;

    private String phoneNumber;

    private String address;

    private String problemDescription;

    private OrderStatus orderStatus;

    private LocalDate scheduledDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ServiceTypeServiceModel getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeServiceModel serviceType) {
        this.serviceType = serviceType;
    }

    public LocationServiceModel getLocation() {
        return location;
    }

    public void setLocation(LocationServiceModel location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}
