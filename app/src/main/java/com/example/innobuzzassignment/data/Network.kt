package com.example.innobuzzassignment.data


import retrofit2.Call
import retrofit2.http.GET

interface Network {

    @GET("posts")
    fun getPosts(): Call<String>

}