package com.example.primarydetail.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.primarydetail.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    /**
     * Retrieve all posts from the database, ordering by id
     *
     * @return The list of posts from the database
     */
    @Query("SELECT * FROM ${Post.TABLE_NAME} ORDER BY ${Post.COLUMN_ID} desc")
    fun getAllPosts(): Flow<List<Post>>

    /**
     * Retrieve the number of posts in the database
     *
     * @return The number of posts in the database
     */
    @Query("SELECT COUNT(*) FROM ${Post.TABLE_NAME}")
    fun getPostsCount(): Int

    /**
     * Retrieve post from the database by id
     *
     * @return The post from the database
     */
    @Query("SELECT * FROM ${Post.TABLE_NAME} WHERE ${Post.COLUMN_ID} = :postId")
    suspend fun postById(postId: Long): Post?

    /**
     * Insert posts into the database
     *
     * @param posts A list of posts to insert
     * @return The row IDs of posts that were inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>): List<Long>

    /**
     * Update the post table and mark specified posts as read
     * @param postIds A list of IDs of posts to update
     */
    @Query("UPDATE ${Post.TABLE_NAME} SET ${Post.COLUMN_READ} = 1 WHERE ${Post.COLUMN_ID} IN (:postIds)")
    suspend fun markRead(postIds: List<Long>)

    /**
     * Update the post table and mark specified post as read
     * @param postId The ID of the post to update
     */
    @Query("UPDATE ${Post.TABLE_NAME} SET ${Post.COLUMN_READ} = 1 WHERE ${Post.COLUMN_ID} = :postId")
    suspend fun markRead(postId: Long)

    /**
     * Delete records in post database by specified IDs
     * @param postIds A list of IDs of posts to delete
     */
    @Query("DELETE FROM ${Post.TABLE_NAME} WHERE ${Post.COLUMN_ID} IN (:postIds)")
    suspend fun deletePosts(postIds: List<Long>)

    /**
     * Delete a record from the post database by specified ID
     * @param postId The ID of the post to delete
     */
    @Query("DELETE FROM ${Post.TABLE_NAME} WHERE ${Post.COLUMN_ID} = :postId")
    suspend fun deletePost(postId: Long)
}
