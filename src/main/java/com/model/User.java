package com.model;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public String getPassword() {
        return password;
    }
}