package com.wonn.githubrepo.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    public final static String BASE_URL = "https://api.github.com/users/";

    // username
    @GET("{username}")
    Call<JsonObject> userInfo(@Path("username")String username);

    // username/repos
    @GET("{username}/repos")
    Call<JsonObject> repos(@Path("username")String username);
}
