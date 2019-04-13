package org.softuni.handy.domain.models.service;

import org.hibernate.validator.constraints.Length;
import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.domain.models.validation_constants.ValidationConstraints;
import org.softuni.handy.domain.models.validation_constants.ValidationMessages;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {

    private String username;

    private String email;

    private String password;

    private Set<UserRole> authorities;

    private boolean accountNonLocked;

    public UserServiceModel() {
        this.setAccountNonLocked(true);

    }

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

    public Set<UserRole> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<UserRole> authorities) {
        this.authorities = authorities;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }


}
