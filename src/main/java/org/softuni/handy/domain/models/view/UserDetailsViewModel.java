package org.softuni.handy.domain.models.view;

import org.softuni.handy.domain.entities.UserRole;

import java.util.Set;

public class UserDetailsViewModel {
    private String  id;

    private String username;

    private String email;

    private Set<UserRole> authorities;

    private boolean accountNonLocked;

    private boolean isRoodAdmin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean isRoodAdmin() {
        return isRoodAdmin;
    }

    public void setRoodAdmin(boolean roodAdmin) {
        isRoodAdmin = roodAdmin;
    }
}
