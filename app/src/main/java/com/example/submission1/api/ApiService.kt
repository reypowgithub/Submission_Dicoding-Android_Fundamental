package com.example.submission1.api

import com.example.submission1.data.DetailUserResponse
import com.example.submission1.data.User
import com.example.submission1.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsername(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username : String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username : String
    ): Call<ArrayList<User>>

}