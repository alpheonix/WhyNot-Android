package com.example.why_not_android.data.Models;

import android.net.Uri;

import java.io.File;

public class Modify {
    private String email;
    private String username;
    private Uri fileUri;
    private File imageFile;
    private String bio;
    private int preferences;
    private int film;
    private int musique;
    private int activite;

    private static Modify modify = null;

    public static Modify getClient() {
        if (modify == null) {
            modify = new Modify();
        }
        return modify;
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
        return "Modify{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", fileUri=" + fileUri +
                ", imageFile=" + imageFile +
                ", bio='" + bio + '\'' +
                ", preferences='" + preferences + '\'' +
                '}';
    }
}
