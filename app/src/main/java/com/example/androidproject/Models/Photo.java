package com.example.androidproject.Models;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("id")
    private String id;

    @SerializedName("urls")
    private PhotoURL url = new PhotoURL();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PhotoURL getUrl() {
        return url;
    }


}
