package com.snc.users;

public abstract class BaseUser {
    private String name;
    private String email;

    public BaseUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserInfo() {
        return "Name: " + name + ", Email: " + email;
    }
}
