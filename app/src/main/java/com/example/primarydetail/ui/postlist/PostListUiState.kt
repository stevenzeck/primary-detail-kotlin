package com.example.primarydetail.ui.postlist

import com.example.primarydetail.model.Post
import com.example.primarydetail.util.AppError

sealed interface PostListUiState {

    data object Loading : PostListUiState

    data class Success(
        val posts: List<Post>,
    ) : PostListUiState

    data class Failed(
        val error: AppError,
    ) : PostListUiState
}
