package com.snc.users;

public class CustomerUser extends BaseUser {
    public CustomerUser(String name, String email) {
        super(name, email);
    }

    public String viewOrders() {
        return "Orders viewed for user: " + getName();
    }
}
