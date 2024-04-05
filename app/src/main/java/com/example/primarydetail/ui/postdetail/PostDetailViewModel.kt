package com.example.primarydetail.ui.postdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primarydetail.ui.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val repository: PostRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val postId: Long = savedStateHandle["postId"] ?: 0L

    val postDetailUiState: StateFlow<PostDetailUiState> =
        repository.postById(postId)
            .map(PostDetailUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PostDetailUiState.Loading,
            )

    /**
     * Mark a post as read via repository
     * @param postId The ID of the post to mark as read
     */
    fun markRead(postId: Long) = viewModelScope.launch {
        repository.markRead(postId)
    }

    /**
     * Delete a post via repository
     */
    fun deletePost() = viewModelScope.launch {
        postId.let {
            repository.deletePost(it)
        }
    }
}
