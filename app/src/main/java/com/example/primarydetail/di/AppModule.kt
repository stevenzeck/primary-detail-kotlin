package com.example.primarydetail.di

import com.example.primarydetail.posts.ui.PostRepository
import com.example.primarydetail.posts.ui.PostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { PostRepository(get(), get()) }

    viewModel { PostViewModel(get()) }

}
