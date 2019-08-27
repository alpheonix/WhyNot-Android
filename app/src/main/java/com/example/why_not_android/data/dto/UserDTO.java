package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class UserDTO {
    @SerializedName("_id")
    private String _id;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("photo")
    private String photo;
    @SerializedName("birthdate")
    private String birthdate;
    @SerializedName("sexe")
    private String sexe;
    @SerializedName("preference")
    private int preference;
    @SerializedName("bio")
    private String bio;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("isDeleted")
    private String isDeleted;
    @SerializedName("banned")
    private String banned;
    @SerializedName("twitter")
    private String twitter;
    @SerializedName("insta")
    private String insta;
    @SerializedName("facebook")
    private String facebook;

    public  String get_id() {
        return _id;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", photo='" + photo + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", sexe='" + sexe + '\'' +
                ", preference='" + preference + '\'' +
                ", bio='" + bio + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", banned='" + banned + '\'' +
                '}';
    }
}
