package com.example.primarydetail.ui.postlist

import com.example.primarydetail.model.Post

sealed interface PostListUiState {

    data object Loading : PostListUiState

    data class Success(
        val posts: List<Post>,
    ) : PostListUiState

    data class Failed(
        val error: Exception,
    ) : PostListUiState
}
