package com.example.innobuzzassignment.data

import android.util.Log
import com.example.innobuzzassignment.data.Room.Post
import com.example.innobuzzassignment.data.Room.PostsDao
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsRepositary(
    val PostNetworkService: Network,
    val PostsDao: PostsDao
) {


    fun getPostsStream(): Flow<List<Post>>{
        return PostsDao.getPosts()
    }

    suspend fun getPosts(){
        withContext(Dispatchers.Default){
            try {
                val result = PostNetworkService.getPosts()
                result.enqueue(
                    object : Callback<String> {
                        override fun onResponse(
                            call: Call<String>,
                            response: Response<String>
                        ) {
                            Log.v("MyActivity","${response}")
                            if (response.isSuccessful){
                                val data = Gson().fromJson(response.body(), Array<Post>::class.java).toList()
                                GlobalScope.launch {
                                    PostsDao.insertAllPosts(data)
                                }
                            }
                            else{
                                Log.v("MyActivity","failed...${response.errorBody()}")
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.v("MyActivity","failed...${t.localizedMessage}")
                        }

                    }
                )
            }catch (e :Exception){
                Log.v("MyActivity","error...${e.localizedMessage}")
            }
        }
    }




}