package com.example.primarydetail.posts.ui

import android.util.Log
import com.example.primarydetail.posts.domain.model.Post
import com.example.primarydetail.posts.services.ApiService
import com.example.primarydetail.posts.services.PostsDao
import com.example.primarydetail.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val client: ApiService, private val postsDao: PostsDao
) {

    /**
     * If there is nothing in the database, get all posts and save them.
     * This function will now emit Resource states to indicate loading, success, or error.
     */
    suspend fun getServerPosts(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            Log.d("Server", "Checking posts in database")
            if (postsDao.getPostsCount() == 0) {
                Log.d("Server", "No posts in database, fetching remote")
                val posts = client.getAllPosts()
                insertPosts(posts)
                emit(Resource.Success(Unit))
            } else {
                Log.d("Server", "Database already populated.")
                emit(Resource.Success(Unit))
            }
        } catch (e: HttpException) {
            Log.e("Server", "Error fetching posts: ${e.message()}", e)
            emit(Resource.Error("Failed to fetch posts: ${e.message()}"))
        } catch (e: IOException) {
            Log.e("Server", "Network error fetching posts: ${e.message}", e)
            emit(Resource.Error("Network error: Please check your connection."))
        } catch (e: Exception) {
            Log.e("Server", "Unknown error fetching posts: ${e.message}", e)
            emit(Resource.Error("An unknown error occurred."))
        }
    }.flowOn(Dispatchers.IO)

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

    suspend fun postById(postId: Long): Post? = postsDao.postById(postId)
}
