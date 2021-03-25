package com.example.dailynotdilly.models;

import java.io.Serializable;
import java.util.UUID;

public class ManifestFeature implements Serializable {

    private static final long serialUID = -12345678912L;
    private UUID id;

    private String name;
    private String description;
    private int imageURL;

    // constructor
    public ManifestFeature(String name, String description, int imageURL) {
        this.id = UUID.randomUUID();
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

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageURL() {
        return imageURL;
    }

    public void setImageURL(int imageURL) {
        this.imageURL = imageURL;
    }
}
