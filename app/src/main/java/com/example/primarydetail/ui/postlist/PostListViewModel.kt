package com.example.primarydetail.ui.postlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primarydetail.model.Post
import com.example.primarydetail.ui.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    // Holds the state of the UI
    private val viewModelState = MutableStateFlow(PostListViewModelState(isLoading = true))
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    // Posts that are selected by long press
    private val _selectedPosts = MutableStateFlow(emptyList<Long>())
    private val selectedPosts get() = _selectedPosts.asStateFlow()

    // Retrieve posts when the ViewModel is first created so list screen can display them
    init {
        refreshPosts()
    }

    fun refreshPosts() {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.getPosts().collect { postList ->
                if (postList.isEmpty()) {
                    repository.getServerPosts()
                } else {
                    viewModelState.update {
                        it.copy(posts = postList, isLoading = false)
                    }
                }
            }
        }
    }

    // Add or remove a post in the selection tracker
    fun toggleSelected(id: Long) {
        _selectedPosts.value = selectedPosts.value.toggle(id)

        if (selectedPosts.value.isEmpty()) {
            endSelection()
        }
        viewModelState.update {
            it.copy(selectedPosts = selectedPosts.value)
        }
    }

    private fun <T> List<T>.toggle(item: T) =
        if (item in this)
            this - item
        else this + item

    // Start selection tracking
    fun startSelection(id: Long) {
        _selectedPosts.value = listOf(id)
        viewModelState.update {
            it.copy(selectionMode = true, selectedPosts = selectedPosts.value)
        }
    }

    // End selection tracking
    fun endSelection() {
        viewModelState.update {
            it.copy(selectionMode = false, selectedPosts = emptyList())
        }
        refreshPosts()
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

sealed interface PostListUiState {

    val isLoading: Boolean
    val errorMessages: List<String>

    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : PostListUiState

    data class HasPosts(
        val posts: List<Post>,
        val selectionMode: Boolean,
        val selectedPosts: List<Long>,
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : PostListUiState
}

private data class PostListViewModelState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val selectionMode: Boolean = false,
    val selectedPosts: List<Long> = emptyList(),
    val errorMessages: List<String> = emptyList(),
) {

    fun toUiState(): PostListUiState =
        if (posts.isEmpty()) {
            PostListUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            PostListUiState.HasPosts(
                posts = posts,
                isLoading = isLoading,
                selectionMode = selectionMode,
                selectedPosts = selectedPosts,
                errorMessages = errorMessages,
            )
        }
}