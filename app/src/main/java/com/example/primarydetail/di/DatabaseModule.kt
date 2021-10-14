package com.example.primarydetail.di

import androidx.room.Room
import com.example.primarydetail.services.PostsDatabase
import com.example.primarydetail.util.DATABASE_NAME
import org.koin.dsl.module

val databaseModule = module {

    single { Room.databaseBuilder(get(), PostsDatabase::class.java, DATABASE_NAME).build() }

    single { get<PostsDatabase>().postsDao() }
}
