package com.example.primarydetail.posts.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.primarydetail.posts.domain.model.Post


@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao

}
