package com.example.androidproject.Models;

import com.google.gson.annotations.SerializedName;

public class PhotoURL {
    @SerializedName("full")
    private String full;
    @SerializedName("regular")
    private String regular;

    public String getRegular() {
        return regular;
    }

}
