package com.example.primarydetail.posts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    // Get posts from the database via repository
    val posts = repository.getPostsFromDatabase()

    // Get posts from the server via repository
    fun serverPosts() {
        viewModelScope.launch {
            repository.getServerPosts()
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
}