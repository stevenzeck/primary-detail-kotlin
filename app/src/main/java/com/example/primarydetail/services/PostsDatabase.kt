package com.example.primarydetail.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.primarydetail.model.Post


@Database(entities = [Post::class], version = 1, exportSchema = true)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao

}
