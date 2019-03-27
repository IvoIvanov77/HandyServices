package org.softuni.handy.domain.models.service;

import org.softuni.handy.domain.entities.Review;
import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.enums.ServiceStatus;

import java.util.Set;

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

    private Set<Review> reviews;

    private byte rating;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }
}
