package org.softuni.handy.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service_types")
public class ServiceType extends PriorityEntity {

    private String serviceName;

    private String servicePicture;

    private List<ProfessionalService> services;

    @Column(name = "service_name", nullable = false, unique = true)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Column(name = "image_url")
    public String getServicePicture() {
        return servicePicture;
    }

    public void setServicePicture(String servicePicture) {
        this.servicePicture = servicePicture;
    }

    @OneToMany(mappedBy = "serviceType")
    public List<ProfessionalService> getServices() {
        return services;
    }

    public void setServices(List<ProfessionalService> services) {
        this.services = services;
    }


}
