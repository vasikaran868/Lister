package com.example.innobuzzassignment.data.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 2, exportSchema = false)
abstract class PostsDatabase: RoomDatabase() {
    abstract fun postDao():PostsDao

    companion object{
        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getDatabase(context: Context): PostsDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsDatabase::class.java,
                    "post_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE= instance
                instance
            }
        }
    }
}
