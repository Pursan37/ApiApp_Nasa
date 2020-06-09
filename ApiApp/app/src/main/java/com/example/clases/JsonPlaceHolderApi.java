package com.example.clases;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("planetary/apod?api_key=uSuae2PDEZjz3kj7Lcj2gfRntRmgnnHORr7m7fFz")
    Call<AstronomyDailyPicture> getPosts();

}
