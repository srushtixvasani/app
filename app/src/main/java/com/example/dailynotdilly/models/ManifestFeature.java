package com.example.dailynotdilly.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "manifest_table")
public class ManifestFeature implements Serializable {

    private static final long serialUID = -12345678912L;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private String imageURL;

    // constructor
    public ManifestFeature(String name, String description, String imageURL) {
//        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", description: " + this.description +
                 ", URL: " + this.imageURL;
    }
}
