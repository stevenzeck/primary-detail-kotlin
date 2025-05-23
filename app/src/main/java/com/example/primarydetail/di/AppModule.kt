package com.example.primarydetail.di

import com.example.primarydetail.services.ApiService
import com.example.primarydetail.services.PostsDao
import com.example.primarydetail.ui.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        client: ApiService,
        postsDao: PostsDao
    ) = PostRepository(client, postsDao)
}
