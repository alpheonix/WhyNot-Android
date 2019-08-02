package com.example.why_not_android.data.Models;

import android.net.Uri;

import java.io.File;

public class Signup {
    private String email;
    private String username;
    private String password;
    private int gender;
    private String birthdate;
    private Uri fileUri;
    private File imageFile;
    private String bio;
    private int preferences;
    private int film;
    private int musique;
    private int activite;

    private static Signup signup = null;

    public static Signup getClient() {
        if (signup == null) {
            signup = new Signup();
        }
        return signup;
    }

    public int getFilm() {
        return film;
    }

    public void setFilm(int film) {
        this.film = film;
    }

    public int getMusique() {
        return musique;
    }

    public void setMusique(int musique) {
        this.musique = musique;
    }

    public int getActivite() {
        return activite;
    }

    public void setActivite(int activite) {
        this.activite = activite;
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
        this.username = capitalize(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getPreferences() {
        return preferences;
    }

    public void setPreferences(int preferences) {
        this.preferences = preferences;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    private String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return "Signup{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", fileUri=" + fileUri +
                ", imageFile=" + imageFile +
                ", bio='" + bio + '\'' +
                ", preferences='" + preferences + '\'' +
                '}';
    }
}
