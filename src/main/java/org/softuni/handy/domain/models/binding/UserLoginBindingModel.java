package org.softuni.handy.domain.models.binding;

import org.hibernate.validator.constraints.Length;

public class UserLoginBindingModel {

    private final static int MIN_USERNAME_LENGTH = 1;
    private final static int MAX_USERNAME_LENGTH = 15;

    private String username;

    private String password;

    @Length(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
