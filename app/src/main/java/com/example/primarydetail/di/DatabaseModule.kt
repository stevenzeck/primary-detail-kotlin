package com.example.primarydetail.di

import android.content.Context
import androidx.room.Room
import com.example.primarydetail.posts.services.PostsDao
import com.example.primarydetail.posts.services.PostsDatabase
import com.example.primarydetail.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): PostsDatabase {
        return Room.databaseBuilder(appContext, PostsDatabase::class.java, DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideDao(postsDatabase: PostsDatabase): PostsDao {
        return postsDatabase.postsDao()
    }
}
