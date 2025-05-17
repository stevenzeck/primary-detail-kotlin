package com.example.primarydetail.ui.postlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primarydetail.ui.PostRepository
import com.example.primarydetail.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<PostListUiState>(PostListUiState.Loading)
    val postListUiState: StateFlow<PostListUiState> = _uiState.asStateFlow()

    private var initialFetchAttemptedOrSucceeded = false

    init {
        loadPosts(isInitialLoad = true)
    }

    fun loadPosts(isInitialLoad: Boolean = false, forceServerRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isInitialLoad || forceServerRefresh || _uiState.value is PostListUiState.Loading) {
                _uiState.value = PostListUiState.Loading
            }

            repository.getPosts()
                .catch { e ->
                    Log.e("PostListViewModel", "Error in posts data flow from repository", e)
                    _uiState.value = PostListUiState.Failed(e as Exception)
                }
                .collect { posts ->
                    val currentlyEmpty = posts.isEmpty()

                    if ((isInitialLoad && !initialFetchAttemptedOrSucceeded && currentlyEmpty) || (forceServerRefresh && currentlyEmpty)) {
                        Log.d(
                            "PostListViewModel",
                            "Local database is empty or refresh forced. Fetching from server..."
                        )
                        _uiState.value = PostListUiState.Loading

                        when (val fetchResult =
                            repository.getServerPosts()) { // Assumes PostRepository is updated
                            is Result.Success -> {
                                Log.d(
                                    "PostListViewModel",
                                    "Server fetch successful. Waiting for DB update."
                                )
                                initialFetchAttemptedOrSucceeded = true
                                if (currentlyEmpty && _uiState.value !is PostListUiState.Failed) {
                                    _uiState.value = PostListUiState.Success(posts)
                                }
                            }

                            is Result.Error -> {
                                Log.e(
                                    "PostListViewModel",
                                    "Failed to fetch posts from server",
                                    fetchResult.exception
                                )
                                initialFetchAttemptedOrSucceeded = true
                                if (currentlyEmpty || forceServerRefresh) {
                                    _uiState.value = PostListUiState.Failed(fetchResult.exception)
                                } else {
                                    _uiState.value = PostListUiState.Success(posts)
                                }
                            }
                        }
                    } else if (forceServerRefresh && !currentlyEmpty) {
                        Log.d("PostListViewModel", "DB not empty, but forcing server refresh...")
                        _uiState.value = PostListUiState.Loading
                        when (val fetchResult = repository.getServerPosts()) {
                            is Result.Success -> {
                                Log.d(
                                    "PostListViewModel",
                                    "Server refresh successful. Waiting for DB update."
                                )
                                initialFetchAttemptedOrSucceeded = true
                            }

                            is Result.Error -> {
                                Log.e(
                                    "PostListViewModel",
                                    "Failed to refresh posts from server",
                                    fetchResult.exception
                                )
                                initialFetchAttemptedOrSucceeded = true
                                _uiState.value = PostListUiState.Success(posts)
                                // TODO: Consider a way to show a non-critical error message to the user (e.g., Snackbar)
                            }
                        }
                    } else {
                        _uiState.value = PostListUiState.Success(posts)
                        if (posts.isNotEmpty()) {
                            initialFetchAttemptedOrSucceeded = true
                        }
                    }
                }
        }
    }

    // Mark posts as read via repository
    fun markRead() = viewModelScope.launch {
        //TODO
    }

    // Mark single post as read
    fun markRead(postId: Long) = viewModelScope.launch {
        repository.markRead(postId = postId)
    }

    fun deletePosts() = viewModelScope.launch {
        //TODO
    }
}
