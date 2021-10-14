package com.example.primarydetail.ui.postlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primarydetail.model.Post
import com.example.primarydetail.ui.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostListViewModel(private val repository: PostRepository) : ViewModel() {

    // Posts via [serverPosts()]
    private val _posts = MutableStateFlow<PostListScreenState>(PostListScreenState.Loading)
    val posts get() = _posts.asStateFlow()

    // Posts that are selected by long press
    private val _selectedPosts = MutableStateFlow(emptyList<Long>())
    val selectedPosts get() = _selectedPosts.asStateFlow()

    // Whether or not items are selected (SelectionTracker)
    private val _selectionMode = MutableStateFlow(false)
    val selectionMode get() = _selectionMode

    init {
        _posts.value = PostListScreenState.Loading
        viewModelScope.launch {
            repository.getServerPosts()
            repository.getPostsFromDatabase().collect { result ->
                _posts.value = PostListScreenState.Success(result)
            }
        }
    }

    // Add or remove a post in the selection tracker
    fun toggleSelected(id: Long) {
        _selectedPosts.value = selectedPosts.value.toggle(id)

        if (selectedPosts.value.isEmpty()) {
            endSelection()
        }
    }

    private fun <T> List<T>.toggle(item: T) =
        if (item in this)
            this - item
        else this + item

    // Start selection tracking
    fun startSelection(id: Long) {
        _selectedPosts.value = listOf(id)
        _selectionMode.value = true
    }

    // End selection tracking
    fun endSelection() {
        _selectionMode.value = false
    }

    // Mark posts as read via repository
    fun markRead() = viewModelScope.launch {
        repository.markRead(selectedPosts.value)
        endSelection()
    }

    fun deletePosts() = viewModelScope.launch {
        repository.deletePosts(selectedPosts.value)
        endSelection()
    }

    sealed class PostListScreenState {

        object Loading : PostListScreenState()

        data class Error(val exception: Exception) : PostListScreenState()

        data class Success(val data: List<Post>) : PostListScreenState()
    }
}