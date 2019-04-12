package org.softuni.handy.domain.models.binding;

import org.hibernate.validator.constraints.Length;
import org.softuni.handy.domain.models.binding.validation_constants.ValidationConstraints;
import org.softuni.handy.domain.models.binding.validation_constants.ValidationMessages;

import javax.validation.constraints.Pattern;

public class UserLoginBindingModel {

    private String username;

    private String password;

    @Length(min = ValidationConstraints.MIN_USERNAME_LENGTH,
            max = ValidationConstraints.MAX_USERNAME_LENGTH,
            message = ValidationMessages.USERNAME_LENGTH_ERROR_MESSAGE)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Pattern(regexp = ValidationConstraints.PASSWORD_PATTERN,
            message = ValidationMessages.INVALID_PASSWORD_ERROR_MESSAGE)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
