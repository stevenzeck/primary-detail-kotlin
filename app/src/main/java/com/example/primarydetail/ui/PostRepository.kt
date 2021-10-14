package com.example.primarydetail.ui

import android.util.Log
import com.example.primarydetail.model.Post
import com.example.primarydetail.services.ApiService
import com.example.primarydetail.services.PostsDao
import com.example.primarydetail.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val client: ApiService,
    private val postsDao: PostsDao
) {

    /**
     * If there is nothing in the database, get all posts and save them, this is just for example
     */
    suspend fun getServerPosts() = withContext(Dispatchers.IO) {
        Log.d("Server", "Checking posts in database")
        if (postsDao.getPostsCount() == 0) {
            Log.d("Server", "No posts in database, fetching remote")
            val posts = client.getAllPosts()
            insertPosts(posts)
        }
    }

    /**
     * Calls the DAO to insert posts into the database
     * @param posts A list of posts to insert
     */
    private suspend fun insertPosts(posts: List<Post>) = postsDao.insertPosts(posts)

    /**
     * Calls the DAO to get posts from the database
     * @return A LiveData list of [Post]
     */
    fun getPostsFromDatabase(): Flow<List<Post>> = postsDao.getAllPosts()

    /**
     * Calls the DAO to get a single post from the database
     * @return A [Post]
     */
    suspend fun postById(postId: Long): Result<Post> {
        val post = postsDao.postById(postId)
        return if (post != null) {
            Result.Success(post)
        } else {
            Result.Error(IllegalArgumentException("Unable to find post"))
        }
    }

    /**
     * Calls the DAO to update posts and mark them as read
     * @param postIds A list of IDs to update
     */
    suspend fun markRead(postIds: List<Long>) = postsDao.markRead(postIds)

    /**
     * Calls the DAO to update a post and mark it as read
     * @param postId The ID to update
     */
    suspend fun markRead(postId: Long) = postsDao.markRead(postId)

    /**
     * Calls the DAO to delete posts
     * @param postIds A list of IDs to delete
     */
    suspend fun deletePosts(postIds: List<Long>) = postsDao.deletePosts(postIds)

    /**
     * Calls the DAO to delete a post
     * @param postId The ID of the post to delete
     */
    suspend fun deletePost(postId: Long) = postsDao.deletePost(postId)
}
