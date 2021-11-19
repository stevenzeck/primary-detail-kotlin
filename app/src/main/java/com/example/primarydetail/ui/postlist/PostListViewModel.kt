package com.example.primarydetail.ui.postlist

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

    //
    init {
        refreshPosts()

        viewModelScope.launch {
            repository.getReadPosts().collect { read ->
                viewModelState.update { it.copy(read = read) }
            }
        }
    }

    fun refreshPosts() {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.getServerPosts()
            val posts = repository.getPostsFromDatabase()
            viewModelState.update {
                when (posts) {
                    is Result.Success -> it.copy(posts = posts.data, isLoading = false)
                    else -> {
                        val errorMessages = it.errorMessages
                        it.copy(errorMessages = errorMessages, isLoading = false)
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
    }

    // Mark posts as read via repository
    fun markRead() = viewModelScope.launch {
        repository.markRead(selectedPosts.value)
        refreshPosts()
        endSelection()
    }

    // Mark single post as read
    fun markRead(postId: Long) = viewModelScope.launch {
        repository.markRead(postId = postId)
    }

    fun deletePosts() = viewModelScope.launch {
        repository.deletePosts(selectedPosts.value)
        refreshPosts()
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
        val read: List<Long>,
        val selectionMode: Boolean,
        val selectedPosts: List<Long>,
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : PostListUiState
}

private data class PostListViewModelState(
    val posts: List<Post> = emptyList(),
    val read: List<Long> = emptyList(),
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
                read = read,
                isLoading = isLoading,
                selectionMode = selectionMode,
                selectedPosts = selectedPosts,
                errorMessages = errorMessages,
            )
        }
}