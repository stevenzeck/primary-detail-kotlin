package com.example.primarydetail.posts.services

import com.example.primarydetail.posts.domain.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    /**
     * Get all posts from the server
     * @return A list of [Post]
     */
    @GET("/posts")
    suspend fun getAllPosts(): List<Post>

    /**
     * Get a single post by its ID
     * @param postId The id of the post to retrieve
     * @return [Post] The post itself
     */
    @GET("/posts/{id}")
    suspend fun getPostById(@Path(value = "id") postId: Int): Post
}
