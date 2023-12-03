package com.example.happyapp.usersRestApi.profile;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String createdOn;

    public User(Long id, String firstName, String lastName, String username) {
        this.id = String.valueOf(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }
    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
