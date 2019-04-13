package org.softuni.handy.domain.models.service;

import org.softuni.handy.domain.models.validation_constants.ValidationMessages;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ServiceTypeServiceModel extends BaseServiceModel
        implements Comparable<ServiceTypeServiceModel> {

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

    @NotBlank(message = ValidationMessages.EMPTY_SERVICE_TYPE_NAME_ERROR_MESSAGE)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @NotNull(message = ValidationMessages.EMPTY_PICTURE_URL_ERROR_MESSAGE)
    public String getServicePicture() {
        return servicePicture;
    }

    public void setServicePicture(String servicePicture) {
        this.servicePicture = servicePicture;
    }

    @Override
    public int compareTo(ServiceTypeServiceModel o) {
        return this.getPriority() - o.getPriority();
    }
}
