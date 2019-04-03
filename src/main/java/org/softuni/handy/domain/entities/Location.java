package org.softuni.handy.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location extends PriorityEntity{

    private String town;

    private String locationPicture;

    private List<ProfessionalService> services;

    @Column(name = "town", nullable = false)
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Column(name = "image_url")
    public String getLocationPicture() {
        return locationPicture;
    }

    public void setLocationPicture(String locationPicture) {
        this.locationPicture = locationPicture;
    }

    @OneToMany(mappedBy = "location")
    public List<ProfessionalService> getServices() {
        return services;
    }

    public void setServices(List<ProfessionalService> services) {
        this.services = services;
    }
}
