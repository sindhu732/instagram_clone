package com.cornell.sindhu.instagram.Models;

import java.io.Serializable;

/**
 * Created by constantin on 12/3/17.
 */

public class Post implements Serializable{
    private String userid;
    private String displayName;
    private String email;
    private boolean privateState;
    private String imageName;
    private String imageUrl;
    private String description;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPrivateState() {
        return privateState;
    }

    public void setPrivateState(boolean privateState) {
        this.privateState = privateState;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    public Post(String userid, String displayName, String email,
                boolean privateState, String imageName, String imageUrl, String description) {
        this.userid = userid;
        this.displayName = displayName;
        this.email = email;
        this.privateState = privateState;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.description = description;

    }

    public Post() {

    }


    public String toString() {
        return "Post{" +
                "userid='" + userid + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", privateState='" + privateState + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
