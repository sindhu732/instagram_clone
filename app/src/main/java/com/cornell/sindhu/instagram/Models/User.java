package com.cornell.sindhu.instagram.Models;

/**
 * Created by sindhu on 12/3/17.
 */

public class User {
    private String userid;
    private String name;
    private String email;

    public User(String userid, String name, String email) {
        this.userid = userid;
        this.name = name;
        this.email = email;
    }

    public User() {

    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return name;
    }

    public void setDisplayName(String name) {
        this.name = name;
    }

    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

