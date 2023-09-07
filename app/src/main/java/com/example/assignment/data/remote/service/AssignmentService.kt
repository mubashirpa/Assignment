package com.example.assignment.data.remote.service

import com.example.assignment.data.remote.dto.comments.CommentsDto
import com.example.assignment.data.remote.dto.posts.PostResult
import com.example.assignment.data.remote.dto.posts.PostsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AssignmentService {

    @GET("/posts")
    suspend fun getPosts(@Query("page") page: Int = 1): PostsDto

    @GET("/posts/{post_id}")
    suspend fun getPost(@Path("post_id") postId: String): PostResult

    @GET("/comments/{post_id}")
    suspend fun getComments(
        @Path("post_id") postId: String,
        @Query("page") page: Int = 1
    ): CommentsDto
}