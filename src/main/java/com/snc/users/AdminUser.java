package com.snc.users;

public class AdminUser extends BaseUser {
    public AdminUser(String name, String email) {
        super(name, email);
    }

    public String resetPassword() {
        return "Password has been reset for user: " + getName();
    }
}
