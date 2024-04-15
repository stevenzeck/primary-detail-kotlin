package com.example.primarydetail.ui.postlist

import com.example.primarydetail.model.Post

sealed interface PostListUiState {

    data object Loading : PostListUiState

    data class Success(
        val posts: List<Post>,
        val selectionMode: Boolean,
        val selectedPosts: List<Long>
    ) : PostListUiState

    data class Failed(
        val error: Exception
    ) : PostListUiState
}
