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
                    val currentUiState = _uiState.value as? PostListUiState.Success
                    _uiState.value = PostListUiState.Success(
                        posts,
                        currentUiState?.selectionMode ?: false,
                        currentUiState?.selectedPosts ?: emptyList()
                    )
                }
            } catch (e: Exception) {
                _uiState.value = PostListUiState.Failed(e)
            }
        }
    }

    private fun <T> List<T>.toggle(item: T) =
        if (item in this) this - item else this + item

    fun toggleSelected(id: Long) {
        val currentUiState = _uiState.value as? PostListUiState.Success ?: return
        val updatedSelectedPosts = currentUiState.selectedPosts.toggle(id)
        _uiState.value = currentUiState.copy(
            selectedPosts = updatedSelectedPosts,
            selectionMode = updatedSelectedPosts.isNotEmpty()
        )
    }

    fun startSelection(id: Long) {
        val currentUiState = _uiState.value as? PostListUiState.Success ?: return
        _uiState.value = currentUiState.copy(
            selectedPosts = listOf(id),
            selectionMode = true
        )
    }

    fun endSelection() {
        val currentUiState = _uiState.value as? PostListUiState.Success ?: return
        _uiState.value = currentUiState.copy(
            selectedPosts = emptyList(),
            selectionMode = false
        )
    }

    fun markRead() = viewModelScope.launch {
        val currentUiState = _uiState.value as? PostListUiState.Success ?: return@launch
        repository.markRead(currentUiState.selectedPosts)
        endSelection()
    }

    fun markRead(postId: Long) = viewModelScope.launch {
        repository.markRead(postId = postId)
    }

    fun deletePosts() = viewModelScope.launch {
        val currentUiState = _uiState.value as? PostListUiState.Success ?: return@launch
        repository.deletePosts(currentUiState.selectedPosts)
        endSelection()
    }
}
