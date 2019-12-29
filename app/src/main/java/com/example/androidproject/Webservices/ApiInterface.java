package com.example.androidproject.Webservices;

import com.example.androidproject.Models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    // @GET comes from Retrofit
    // Will call getPhotos and will make a request to /photos URI
    @GET("photos")
    Call<List<Photo>> getPhotos();

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") String id);
}
