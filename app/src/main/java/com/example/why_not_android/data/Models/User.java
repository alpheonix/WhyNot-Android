package com.example.why_not_android.data.Models;

public class User {
    private  String _id;
    private String email;
    private  String username;
    private  String photo;
    private  String birthdate;
    private String sexe;
    private int preference;
    private  String bio;
    private String createdAt;
    private String isDeleted;
    private String banned;

    public  String get_id() {
        return _id;
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

    public  String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public  String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public  String getBirthdate() {
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

    public  String getBio() {
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
