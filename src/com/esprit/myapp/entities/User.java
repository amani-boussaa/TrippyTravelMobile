package com.esprit.myapp.entities;

import java.util.Arrays;

public class User {
    private int id;
    private String email;
    private String[]  roles;
    private String password;
    private boolean isVerified;
    private String firstname;
    private String lastname;
    private String image;

    public User(int id, String email, String[] roles, String password, boolean isVerified, String firstname, String lastname, String image) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.isVerified = isVerified;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
    }

    public User(String firstname, String lastname,String email, String password ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", password='" + password + '\'' +
                ", isVerified=" + isVerified +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}