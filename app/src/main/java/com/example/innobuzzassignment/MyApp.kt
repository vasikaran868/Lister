package com.example.innobuzzassignment

import android.app.Application
import android.util.Log
import com.example.innobuzzassignment.data.Network
import com.example.innobuzzassignment.data.PostsRepositary
import com.example.innobuzzassignment.data.Room.PostsDatabase
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MyApp:Application() {

    val BASE_URL = "https://jsonplaceholder.typicode.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder()
            .setLenient()
            .create()))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService = retrofit.create(Network::class.java)

    val database: PostsDatabase by lazy {
        PostsDatabase.getDatabase(this)
    }

    lateinit var postsRepositary: PostsRepositary

    override fun onCreate() {
        super.onCreate()
        Log.v("MyActivity","${database}")
        postsRepositary = PostsRepositary(
            retrofitService,
            database.postDao()
        )
        Log.v("MyActivity","${postsRepositary}")
    }

}