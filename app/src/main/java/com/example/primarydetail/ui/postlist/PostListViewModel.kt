package com.example.primarydetail.ui.postlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primarydetail.ui.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<PostListUiState>(PostListUiState.Loading)
    val uiState: StateFlow<PostListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.getPosts().collect { posts ->
                    _uiState.value = PostListUiState.Success(
                        posts,
                    )
                }
            } catch (e: Exception) {
                _uiState.value = PostListUiState.Failed(e)
            }
        }
    }

    fun markRead() = viewModelScope.launch {
        //TODO: Implement
    }

    fun markRead(postId: Long) = viewModelScope.launch {
        repository.markRead(postId = postId)
    }

    fun deletePosts() = viewModelScope.launch {
        //TODO: Implement
    }
}
