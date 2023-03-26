package com.example.innobuzzassignment.data.Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface PostsDao {

    @Query("SELECT * from post")
    fun getPosts(): Flow<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(post: Post)

    @Update
    suspend fun update(post: Post)

    @Delete
    suspend fun delete(post:Post)

}
