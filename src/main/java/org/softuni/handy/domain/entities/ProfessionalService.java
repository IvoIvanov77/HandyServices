package org.softuni.handy.domain.entities;

import org.softuni.handy.domain.enums.ServiceStatus;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "professional_services")
public class ProfessionalService extends CustomerAction {
    private String slogan;

    private String serviceDescription;

    private ServiceStatus serviceStatus;

    private Set<Review> reviews;

    private byte rating;

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

    @OneToMany(mappedBy = "recipient")
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Column(name = "rating")
    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }
}
