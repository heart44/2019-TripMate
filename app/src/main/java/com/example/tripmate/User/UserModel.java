package com.example.tripmate.User;

public class UserModel {
    private String userName;
    private String profileImageUrl;
    private String uid;

    public UserModel(){}

    public UserModel(String userName, String profileImageUrl, String uid) {
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
