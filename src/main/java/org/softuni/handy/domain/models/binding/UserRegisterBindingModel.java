package org.softuni.handy.domain.models.binding;

import org.hibernate.validator.constraints.Length;

public class UserRegisterBindingModel {

    private final static int MIN_USERNAME_LENGTH = 1;
    private final static int MAX_USERNAME_LENGTH = 15;

    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    @Length(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
