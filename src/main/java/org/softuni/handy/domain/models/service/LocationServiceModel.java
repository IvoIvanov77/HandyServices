package org.softuni.handy.domain.models.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class LocationServiceModel extends BaseServiceModel {
    private int priority;

    private String town;

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
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
