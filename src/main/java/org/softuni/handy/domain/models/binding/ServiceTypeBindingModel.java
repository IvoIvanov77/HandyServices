package org.softuni.handy.domain.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ServiceTypeBindingModel {
    private int priority;

    private String serviceTypeName;

    private String picture;

    @NotNull
    @Min(1)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @NotNull
    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
