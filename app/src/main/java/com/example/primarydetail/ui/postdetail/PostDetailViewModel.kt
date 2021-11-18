package com.example.primarydetail.ui.postdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primarydetail.model.Post
import com.example.primarydetail.ui.PostRepository
import com.example.primarydetail.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val repository: PostRepository,
    private val state: SavedStateHandle
) :
    ViewModel() {

    // UI state exposed to the UI
    private val viewModelState = MutableStateFlow(PostDetailViewModelState(isLoading = true))
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        retrievePost()
    }

    private fun retrievePost() {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val postId = state.get<Long>("postId")
            val post = postId?.let { repository.postById(it) }
            viewModelState.update {
                when (post) {
                    is Result.Success -> it.copy(post = post.data, isLoading = false)
                    else -> {
                        val errorMessages = it.errorMessages
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }
    }

    /**
     * Mark a post as read via repository
     * @param postId The ID of the post to mark as read
     */
    fun markRead(postId: Long) = viewModelScope.launch {
        repository.markRead(postId)
    }

    /**
     * Delete a post via repository
     * @param postId The ID of the post to delete
     */
    fun deletePost(postId: Long) = viewModelScope.launch {
        repository.deletePost(postId)
    }
}

sealed interface PostDetailUiState {

    val isLoading: Boolean
    val errorMessages: List<String>

    data class NoPost(
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : PostDetailUiState

    data class HasPost(
        val post: Post,
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : PostDetailUiState
}

private data class PostDetailViewModelState(
    val post: Post? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<String> = emptyList(),
) {

    fun toUiState(): PostDetailUiState =
        if (post == null) {
            PostDetailUiState.NoPost(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            PostDetailUiState.HasPost(
                post = post,
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        }
}