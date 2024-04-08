package com.example.primarydetail.ui.postlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primarydetail.ui.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    // Posts that are selected by long press
    private val _selectedPosts = MutableStateFlow(emptyList<Long>())
    private val selectedPosts get() = _selectedPosts.asStateFlow()

    val postListUiState: StateFlow<PostListUiState> = combine(
        repository.getPosts(),
        selectedPosts
    ) { posts, selectedPosts ->
        if (selectedPosts.isNotEmpty()) {
            PostListUiState.Success(posts, true, selectedPosts)
        } else {
            PostListUiState.Success(posts, false, emptyList())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PostListUiState.Loading,
    )

    // Add or remove a post in the selection tracker
    fun toggleSelected(id: Long) {
        _selectedPosts.value = selectedPosts.value.toggle(id)

        if (selectedPosts.value.isEmpty()) {
            endSelection()
        }
//        postListUiState.update {
//            it.copy(selectedPosts = selectedPosts.value)
//        }
    }

    private fun <T> List<T>.toggle(item: T) =
        if (item in this)
            this - item
        else this + item

    // Start selection tracking
    fun startSelection(id: Long) {
        _selectedPosts.value = listOf(id)
//        viewModelState.update {
//            it.copy(selectionMode = true, selectedPosts = selectedPosts.value)
//        }
    }

    // End selection tracking
    fun endSelection() {
//        viewModelState.update {
//            it.copy(selectionMode = false, selectedPosts = emptyList())
//        }
//        refreshPosts()
    }

    // Mark posts as read via repository
    fun markRead() = viewModelScope.launch {
        repository.markRead(selectedPosts.value)
        endSelection()
    }

    // Mark single post as read
    fun markRead(postId: Long) = viewModelScope.launch {
        repository.markRead(postId = postId)
    }

    fun deletePosts() = viewModelScope.launch {
        repository.deletePosts(selectedPosts.value)
        endSelection()
    }
}
