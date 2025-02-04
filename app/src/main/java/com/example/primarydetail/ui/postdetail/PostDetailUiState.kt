package com.example.primarydetail.ui.postdetail

import com.example.primarydetail.model.Post

sealed interface PostDetailUiState {

    data object Loading : PostDetailUiState

    data class Success(
        val post: Post
    ) : PostDetailUiState

    data class Failed(
        val error: Throwable,
    ) : PostDetailUiState
}