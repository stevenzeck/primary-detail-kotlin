package com.example.primarydetail.di

import com.example.primarydetail.ui.PostRepository
import com.example.primarydetail.ui.postdetail.PostDetailViewModel
import com.example.primarydetail.ui.postlist.PostListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { PostRepository(get(), get()) }

    viewModel { PostListViewModel(get()) }

    viewModel { parameters -> PostDetailViewModel(get(), postId = parameters.get()) }
}
