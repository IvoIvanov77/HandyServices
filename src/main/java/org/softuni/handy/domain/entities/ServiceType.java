package org.softuni.handy.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service_types")
public class ServiceType extends PriorityEntity {

    private String serviceName;

    private String servicePicture;

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


}
