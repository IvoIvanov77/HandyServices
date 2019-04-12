package org.softuni.handy.domain.models.binding;

import org.softuni.handy.domain.models.binding.validation_constants.ValidationMessages;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ServiceTypeBindingModel {

    private int priority;

    private String serviceTypeName;

    private MultipartFile image;

    @NotNull
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @NotBlank(message = ValidationMessages.EMPTY_SERVICE_TYPE_NAME_ERROR_MESSAGE)
    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    @NotNull(message = ValidationMessages.EMPTY_PICTURE_URL_ERROR_MESSAGE)
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
