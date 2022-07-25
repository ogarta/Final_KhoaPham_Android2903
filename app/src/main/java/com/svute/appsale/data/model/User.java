package com.svute.appsale.data.model;

/**
 * Created by Ogata on 7/25/2022.
 */
public class User {
    private String email;
    private String name;
    private String phone;
    private int userGroup;
    private String registerDate;
    private String token;

    public User(String email, String name, String phone, int userGroup, String registerDate, String token) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.userGroup = userGroup;
        this.registerDate = registerDate;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(int userGroup) {
        this.userGroup = userGroup;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", userGroup=" + userGroup +
                ", registerDate='" + registerDate + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
