package org.softuni.handy.domain.models.binding;

import org.hibernate.validator.constraints.Length;
import org.softuni.handy.domain.models.validation_constants.ValidationConstraints;
import org.softuni.handy.domain.models.validation_constants.ValidationMessages;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
public class UserRegisterBindingModel {

    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    @Length(min = ValidationConstraints.MIN_USERNAME_LENGTH, max = ValidationConstraints.MAX_USERNAME_LENGTH,
            message = ValidationMessages.USERNAME_LENGTH_ERROR_MESSAGE)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Email(regexp = ValidationConstraints.EMAIL_PATTERN,
            message = ValidationMessages.INVALID_EMAIL_ERROR_MESSAGE)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Pattern(regexp = ValidationConstraints.PASSWORD_PATTERN,
            message = ValidationMessages.INVALID_PASSWORD_ERROR_MESSAGE)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Pattern(regexp = ValidationConstraints.PASSWORD_PATTERN,
            message = ValidationMessages.INVALID_PASSWORD_ERROR_MESSAGE)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
