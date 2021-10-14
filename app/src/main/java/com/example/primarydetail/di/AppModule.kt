package com.example.primarydetail.di

import com.example.primarydetail.posts.services.ApiService
import com.example.primarydetail.posts.services.PostsDao
import com.example.primarydetail.posts.ui.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(
        client: ApiService,
        postsDao: PostsDao
    ) = PostRepository(client, postsDao)

}
