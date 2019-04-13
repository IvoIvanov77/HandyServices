package org.softuni.handy.domain.models.service;

import org.softuni.handy.domain.models.validation_constants.ValidationMessages;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LocationServiceModel extends BaseServiceModel implements Comparable<LocationServiceModel> {
    private int priority;

    private String town;

    private String locationPicture;

    @NotNull
    @Min(1)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @NotBlank(message = ValidationMessages.EMPTY_LOCATION_NAME_ERROR_MESSAGE)
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @NotNull(message = ValidationMessages.EMPTY_PICTURE_URL_ERROR_MESSAGE)
    public String getLocationPicture() {
        return locationPicture;
    }

    public void setLocationPicture(String locationPicture) {
        this.locationPicture = locationPicture;
    }

    @Override
    public int compareTo(LocationServiceModel o) {
        return this.getPriority() - o.getPriority();
    }
}
