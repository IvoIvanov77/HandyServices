package org.softuni.handy.domain.models.view;

import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.enums.OrderStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class OrderListViewModel {

    private String id;

    private String firstName;

    private String lastName;

    private Location location;

    private ServiceType serviceType;

    private String phoneNumber;

    private LocalDate scheduledDate;

    private OrderStatus orderStatus;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
