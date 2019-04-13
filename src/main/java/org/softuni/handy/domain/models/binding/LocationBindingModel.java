package org.softuni.handy.domain.models.binding;

import org.softuni.handy.domain.models.validation_constants.ValidationMessages;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LocationBindingModel {

    private int priority;

    private String town;

    private MultipartFile imageUrl;

    @NotNull
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
    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }
}
