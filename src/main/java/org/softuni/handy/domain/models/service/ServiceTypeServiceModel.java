package org.softuni.handy.domain.models.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ServiceTypeServiceModel extends BaseServiceModel {
    private int priority;

    private String serviceName;

    private String servicePicture;

    @NotNull
    @Min(1)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @NotNull
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePicture() {
        return servicePicture;
    }

    public void setServicePicture(String servicePicture) {
        this.servicePicture = servicePicture;
    }
}
