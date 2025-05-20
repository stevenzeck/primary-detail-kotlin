package com.example.primarydetail.posts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primarydetail.posts.domain.model.Post
import com.example.primarydetail.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    // Get posts from the database via repository
    val posts = repository.getPostsFromDatabase()

    // StateFlow for server post fetching status
    private val _serverPostStatus = MutableStateFlow<Resource<Unit>>(Resource.Success(Unit))
    val serverPostStatus: StateFlow<Resource<Unit>> = _serverPostStatus.asStateFlow()

    // Get posts from the server via repository
    fun fetchPostsFromServer() {
        viewModelScope.launch {
            repository.getServerPosts().onEach { resource ->
                _serverPostStatus.value = resource
            }.launchIn(viewModelScope)
        }
    }

    /**
     * Mark posts as read via repository
     * @param postIds A list of IDs of posts to mark as read
     */
    fun markRead(postIds: List<Long>) = viewModelScope.launch {
        repository.markRead(postIds)
    }

    /**
     * Mark a post as read via repository
     * @param postId The ID of the post to mark as read
     */
    fun markRead(postId: Long) = viewModelScope.launch {
        repository.markRead(postId)
    }

    /**
     * Delete posts via repository
     * @param postIds A list of IDs of posts to delete
     */
    fun deletePosts(postIds: List<Long>) = viewModelScope.launch {
        repository.deletePosts(postIds)
    }

    /**
     * Delete a post via repository
     * @param postId The ID of the post to delete
     */
    fun deletePost(postId: Long) = viewModelScope.launch {
        repository.deletePost(postId)
    }

    private val _selectedPost = MutableStateFlow<Resource<Post?>>(Resource.Loading())
    val selectedPost: StateFlow<Resource<Post?>> = _selectedPost.asStateFlow()

    fun fetchPostById(postId: Long) {
        viewModelScope.launch {
            _selectedPost.value = Resource.Loading()
            try {
                val post = repository.postById(postId)
                if (post != null) {
                    _selectedPost.value = Resource.Success(post)
                } else {
                    _selectedPost.value = Resource.Error("Post not found.")
                }
            } catch (e: Exception) {
                _selectedPost.value = Resource.Error("Error fetching post details: ${e.message}")
            }
        }
    }
}
