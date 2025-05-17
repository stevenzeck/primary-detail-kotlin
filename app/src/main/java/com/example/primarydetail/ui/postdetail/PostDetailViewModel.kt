package com.example.primarydetail.ui.postdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primarydetail.model.Post
import com.example.primarydetail.ui.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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

    private val postId: Long = savedStateHandle["postId"] ?: -1L

    val postDetailUiState: StateFlow<PostDetailUiState> =
        repository.postById(postId)
            .map<Post, PostDetailUiState> { post ->
                PostDetailUiState.Success(post)
            }
            .catch { e ->
                Log.e("PostDetailViewModel", "Error fetching post details for ID $postId", e)
                emit(PostDetailUiState.Failed(e as Exception))
            }
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
