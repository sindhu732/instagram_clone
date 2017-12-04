package com.cornell.sindhu.instagram.Models;

import java.io.Serializable;

/**
 * Created by constantin on 12/3/17.
 */

public class Post implements Serializable{
    private String userid;
    private String name;
    private String email;
    private boolean privateState;
    private String imageUrl;
    private String description;

    public Post(String userid, String name, String email,
                boolean privateState, String imageUrl, String description) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.privateState = privateState;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Post() {

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

    public boolean getPrivateState() {
        return privateState;
    }

    public void setPrivateState(boolean privateState) {
        this.privateState = privateState;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String toString() {
        return "Post{" +
                "userid='" + userid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", privateState='" + privateState + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
