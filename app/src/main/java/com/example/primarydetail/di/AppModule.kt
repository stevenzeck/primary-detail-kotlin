package com.example.primarydetail.di

import com.example.primarydetail.services.ApiService
import com.example.primarydetail.services.PostsDao
import com.example.primarydetail.ui.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(
        client: ApiService,
        postsDao: PostsDao,
        ioDispatcher: CoroutineDispatcher
    ) = PostRepository(client, postsDao, ioDispatcher)

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
