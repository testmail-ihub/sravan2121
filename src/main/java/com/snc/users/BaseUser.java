package com.snc.users;

public class BaseUser {
    private String name;
    private String email;

    public BaseUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserInfo() {
        return "Name: " + name + ", Email: " + email;
    }
}
